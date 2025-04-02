package com.master.chat.llm.locallm;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.common.constant.AuthConstant;
import com.master.chat.llm.base.key.KeyUpdater;
import com.master.chat.llm.locallm.base.entity.BaseChatCompletion;
import com.master.chat.llm.locallm.base.interceptor.LocalLMInterceptor;
import com.master.chat.llm.locallm.base.interceptor.LocalLMLogger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * LocalLM client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class LocalLMClient implements KeyUpdater {
    @NotNull
    @Getter
    @Setter
    private String apiKey;

    @Getter
    private OkHttpClient okHttpClient;

    private LocalLMClient(Builder builder) {
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
     * 创建默认的OkHttpClient
     */
    private OkHttpClient okHttpClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LocalLMLogger());
        httpLoggingInterceptor.setLevel(level);
        return new OkHttpClient.Builder()
                .addInterceptor(new LocalLMInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 流式响应
     *
     * @param
     * @param
     * @param
     */
    public Response streamChat(BaseChatCompletion chat, String domain, String url) {
        chat.setStream(true);
        try {
            Request request = new Request.Builder().url(domain + url)
                    .addHeader(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + this.apiKey)
                    .addHeader("X-Failover-Enabled", "true")
                    .addHeader("Accept", "text/event-stream")
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chat))).build();
            return okHttpClient.newCall(request).execute();
        } catch (Exception e) {
            log.error("请求参数解析异常：", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String supportModel() {
        return ChatModelEnum.LOCALLM.getValue();
    }

    @Override
    public void updateKey(KeyModel keyModel) {
        this.setApiKey(keyModel.getAppKey());
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

        private String apiKey;

        private OkHttpClient okHttpClient;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder() {
        }

        public Builder apiKey(String val) {
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

        public LocalLMClient build() {
            return new LocalLMClient(this);
        }
    }

}

