package com.master.chat.llm.internlm;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.llm.internlm.constant.ApiConstant;
import com.master.chat.llm.internlm.entity.ModelsList;
import com.master.chat.llm.internlm.entity.request.ChatCompletion;
import com.master.chat.llm.internlm.entity.response.ChatResponse;
import com.master.chat.llm.internlm.interceptor.InternlmInterceptor;
import com.master.chat.llm.internlm.interceptor.InternlmLogger;
import com.master.chat.llm.internlm.listener.SSEListener;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.AuthConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 月之暗面client
 * 文档地址：https://internlm.intern-ai.org.cn/api/document
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class InternlmClient {
    @NotNull
    @Getter
    @Setter
    private String token;

    @Getter
    private OkHttpClient okHttpClient;

    private InternlmClient(Builder builder) {
        token = builder.token;
        if (Objects.isNull(builder.okHttpClient)) {
            log.info("提示：禁止在生产环境使用BODY级别日志，可以用：NONE,BASIC,HEADERS");
            if (Objects.isNull(builder.logLevel)) {
                builder.logLevel = HttpLoggingInterceptor.Level.HEADERS;
            }
            builder.okHttpClient = this.okHttpClient(builder.logLevel);
        }
        okHttpClient = builder.okHttpClient;
    }

    /**
     * 获取模型列表
     *
     * @return
     * @throws IOException
     */
    public ResponseInfo<ModelsList> listModels() {
        try {
            Request request = new Request.Builder().url(ApiConstant.CHAT_LIST_MODELS_URL)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.token).build();
            Response response = okHttpClient.newCall(request).execute();
            String body = response.body().string();
            return ResponseInfo.success(JSON.parseObject(body, ModelsList.class));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseInfo.error();
        }
    }

    /**
     * 对话
     *
     * @param request
     * @return
     * @throws IOException
     */
    public ResponseInfo<ChatResponse> chat(ChatCompletion chat) {
        try {
            Request request = new Request.Builder().url(ApiConstant.CHAT_COMPLETION_URL)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.token)
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chat))).build();
            Response response = okHttpClient.newCall(request).execute();
            String body = response.body().string();
            return ResponseInfo.success(JSON.parseObject(body, ChatResponse.class));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseInfo.error();
        }
    }

    /**
     * 流式响应
     *
     * @param request
     * @param query
     */
    public Boolean streamChat(HttpServletResponse response, ChatCompletion chat, Long chatId, String parentMessageId, String version, String uid) {
        chat.stream = true;
        try {
            Request request = new Request.Builder().url(ApiConstant.CHAT_COMPLETION_URL)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.token)
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chat))).build();
            Response callResponse = okHttpClient.newCall(request).execute();
            if (callResponse.code() != 200) {
                log.error("书生浦语流式响应失败: " + callResponse.body().string());
                return true;
            }
            SSEListener sseListener = new SSEListener(response, chatId, parentMessageId, version, uid);
            return sseListener.streamChat(callResponse);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 创建默认的OkHttpClient
     */
    private OkHttpClient okHttpClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new InternlmLogger());
        httpLoggingInterceptor.setLevel(level);
        return new OkHttpClient.Builder()
                .addInterceptor(new InternlmInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 构造
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull String token;

        private OkHttpClient okHttpClient;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder() {
        }

        public Builder token(@NotNull String val) {
            token = val;
            return this;
        }

        public Builder logLevel(HttpLoggingInterceptor.Level val) {
            logLevel = val;
            return this;
        }

        public Builder okHttpClient(OkHttpClient val) {
            okHttpClient = val;
            return this;
        }

        public InternlmClient build() {
            return new InternlmClient(this);
        }
    }

}
