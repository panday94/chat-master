package com.master.chat.api.moonshot;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.api.moonshot.constant.MoonshotApiConstant;
import com.master.chat.api.moonshot.entity.ChatCompletionRequest;
import com.master.chat.api.moonshot.entity.ChatCompletionResponse;
import com.master.chat.api.moonshot.entity.ModelsList;
import com.master.chat.api.moonshot.interceptor.MoonshotInterceptor;
import com.master.chat.api.moonshot.interceptor.MoonshotLogger;
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
 * 文档地址：https://platform.moonshot.cn/docs/api-reference#%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class MoonshotClient {

    @NotNull
    @Getter
    @Setter
    private String apiKey;

    @Getter
    private OkHttpClient okHttpClient;

    private MoonshotClient(Builder builder) {
        apiKey = builder.apiKey;
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
            Request request = new Request.Builder().url(MoonshotApiConstant.CHAT_LIST_MODELS_URL)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.apiKey).build();
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
    public ResponseInfo<ChatCompletionResponse> chat(ChatCompletionRequest chat) {
        try {
            Request request = new Request.Builder().url(MoonshotApiConstant.CHAT_COMPLETION_URL)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.apiKey)
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chat))).build();
            Response response = okHttpClient.newCall(request).execute();
            String body = response.body().string();
            return ResponseInfo.success(JSON.parseObject(body, ChatCompletionResponse.class));
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
    public Boolean streamChat(HttpServletResponse response, ChatCompletionRequest chat, Long chatId, String parentMessageId, String version) {
        chat.stream = true;
        try {
            Request request = new Request.Builder().url(MoonshotApiConstant.CHAT_COMPLETION_URL)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.apiKey)
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chat))).build();
            Response callResponse = okHttpClient.newCall(request).execute();
            if (callResponse.code() != 200) {
                log.error("月之暗面流式响应失败: " + callResponse.body().string());
                return true;
            }
            MoonshotApi api = new MoonshotApi(response, chatId, parentMessageId, version);
            return api.streamChat(callResponse);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 创建默认的OkHttpClient
     */
    private OkHttpClient okHttpClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new MoonshotLogger());
        httpLoggingInterceptor.setLevel(level);
        return new OkHttpClient.Builder()
                .addInterceptor(new MoonshotInterceptor())
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
        private @NotNull String apiKey;

        private OkHttpClient okHttpClient;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder() {
        }

        public Builder apiKey(@NotNull String val) {
            apiKey = val;
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

        public MoonshotClient build() {
            return new MoonshotClient(this);
        }
    }

}
