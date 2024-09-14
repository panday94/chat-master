package com.master.chat.llm.locallm;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.locallm.chatchat.entity.ChatCompletion;
import com.master.chat.llm.locallm.chatchat.enums.ModelEnum;
import com.master.chat.llm.locallm.chatchat.interceptor.LangchainInterceptor;
import com.master.chat.llm.locallm.chatchat.interceptor.LangchainLogger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * LangChain-Chatchat client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 MasterComputer Corporation Limited All rights reserved.
 */
@NoArgsConstructor(force = true)
@Slf4j
public class LocalLMClient {

    @NotNull
    @Getter
    @Setter
    private String accessToken;

    @NotNull
    @Getter
    @Setter
    private String apiKey;

    @Getter
    private OkHttpClient okHttpClient;

    private LocalLMClient(Builder builder) {
        accessToken = builder.accessToken;
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
     * 构造
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 流式响应
     *
     * @param messages
     * @param eventSourceListener
     * @param model
     */
    public void streamChat(ChatCompletion chat, EventSourceListener eventSourceListener, String domain, ModelEnum model) {
        if (Objects.isNull(eventSourceListener)) {
            throw new LLMException("参数异常：EventSourceListener不能为空");
        }
        chat.setStream(true);
        try {
            EventSource.Factory factory = EventSources.createFactory(this.okHttpClient);
            Request request = new Request.Builder().url(domain + model.getUrl())
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chat))).build();
            factory.newEventSource(request, eventSourceListener);
        } catch (Exception e) {
            log.error("请求参数解析异常：", e);
            e.printStackTrace();
        }
    }

    /**
     * 创建默认的OkHttpClient
     */
    private OkHttpClient okHttpClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LangchainLogger());
        httpLoggingInterceptor.setLevel(level);
        return new OkHttpClient.Builder()
                .addInterceptor(new LangchainInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();
    }

    public static final class Builder {
        private String accessToken;

        private String apiKey;

        private OkHttpClient okHttpClient;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder() {
        }

        public Builder accessToken(String val) {
            accessToken = val;
            return this;
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

