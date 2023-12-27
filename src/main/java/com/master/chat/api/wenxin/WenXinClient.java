package com.master.chat.api.wenxin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.api.wenxin.entity.ChatBody;
import com.master.chat.api.wenxin.entity.ChatResponse;
import com.master.chat.api.wenxin.entity.MessageItem;
import com.master.chat.api.wenxin.config.WenXinConfig;
import com.master.chat.api.wenxin.constant.ModelE;
import com.master.chat.api.wenxin.exception.WenXinException;
import com.master.chat.api.wenxin.interceptor.WenXinInterceptor;
import com.master.chat.api.wenxin.interceptor.WenXinLogger;
import com.master.common.validator.ValidatorUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 文心一言
 * 文档地址：https://cloud.baidu.com/doc/WENXINWORKSHOP/s/flfmc9do2
 *
 * @author Yang
 * @version 1.0.0
 * @date 2023/8/4 13:03
 */
@NoArgsConstructor(force = true)
@Slf4j
public class WenXinClient {

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

    private WenXinClient(Builder builder) {
        if (StrUtil.isBlank(builder.accessToken)) {
            throw new WenXinException("构造错误: accessToken不能为空");
        }
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


    public void streamChat(List<MessageItem> messages, EventSourceListener eventSourceListener, ModelE modelE) {
        ChatBody chatBody = ChatBody.builder().messages(messages).stream(true).build();
        this.streamChat(chatBody, eventSourceListener, modelE);
    }

    public ChatResponse chat(List<MessageItem> messages, ModelE modelE) {
        ChatBody chatBody = ChatBody.builder().messages(messages).build();
        return this.chat(chatBody, modelE);
    }

    public ChatResponse chat(ChatBody chatBody, ModelE modelE) {
        try {
            Request request = new Request.Builder().url(assembleUrl(modelE))
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chatBody))).build();
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
                String result = response.body().string();
                JSONObject jsonObject = JSONUtil.parseObj(result);
                if (ValidatorUtil.isNotNull(jsonObject.get("error_msg"))) {
                    log.error("文心一言大模型接口请求异常，异常原因：{}", jsonObject.getStr("error_msg"));
                }
                return jsonObject.toBean(ChatResponse.class);
            } catch (IOException e) {
                throw new IOException(e);
            }
        } catch (Exception e) {
            log.error("请求参数解析异常：", e);
            e.printStackTrace();
        }
        return new ChatResponse();
    }

    public void streamChat(ChatBody chatBody, EventSourceListener eventSourceListener, ModelE modelE) {
        if (Objects.isNull(eventSourceListener)) {
            throw new WenXinException("参数异常：EventSourceListener不能为空");
        }
        chatBody.setStream(true);
        try {
            EventSource.Factory factory = EventSources.createFactory(this.okHttpClient);
            Request request = new Request.Builder().url(assembleUrl(modelE))
                    .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()),
                            new ObjectMapper().writeValueAsString(chatBody))).build();
            factory.newEventSource(request, eventSourceListener);
        } catch (Exception e) {
            log.error("请求参数解析异常：", e);
            e.printStackTrace();
        }
    }


    private String assembleUrl(ModelE modelE) {
        accessToken = WenXinConfig.refreshAccessToken();
        return modelE.getApiHost() + "?access_token=" + accessToken;
    }

    /**
     * 创建默认的OkHttpClient
     */
    private OkHttpClient okHttpClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new WenXinLogger());
        httpLoggingInterceptor.setLevel(level);
        return new OkHttpClient.Builder()
                .addInterceptor(new WenXinInterceptor())
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
        private @NotNull String accessToken;

        private @NotNull String apiKey;

        private OkHttpClient okHttpClient;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder() {
        }

        public Builder accessToken(@NotNull String val) {
            accessToken = val;
            return this;
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

        public WenXinClient build() {
            return new WenXinClient(this);
        }
    }

}
