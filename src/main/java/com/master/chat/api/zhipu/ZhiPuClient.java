package com.master.chat.api.zhipu;

import cn.hutool.core.util.StrUtil;
import com.master.chat.api.zhipu.interceptor.ZhiPuLogger;
import com.master.common.exception.BusinessException;
import com.zhipu.oapi.ClientV3;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.httpclient.OkHttpTransport;
import com.zhipu.oapi.service.v3.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 智谱清言client
 *
 * @author: yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class ZhiPuClient {

    @NotNull
    @Getter
    @Setter
    private String appKey;

    @NotNull
    @Getter
    @Setter
    private String appSecret;

    @Getter
    private OkHttpClient okHttpClient;

    @Getter
    private ClientV3 clientV3;

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "master-%d";

    private ZhiPuClient(ZhiPuClient.Builder builder) {
        if (StrUtil.isBlank(builder.appKey)) {
            throw new BusinessException("构造错误: accessToken不能为空");
        }
        appKey = builder.appKey;
        appSecret = builder.appSecret;

        if (Objects.isNull(builder.okHttpClient)) {
            log.info("提示：禁止在生产环境使用BODY级别日志，可以用：NONE,BASIC,HEADERS");
            if (Objects.isNull(builder.logLevel)) {
                builder.logLevel = HttpLoggingInterceptor.Level.HEADERS;
            }
            builder.okHttpClient = this.okHttpClient(builder.logLevel);
        }
        okHttpClient = builder.okHttpClient;
        clientV3 = new ClientV3.Builder(appKey, appSecret)
                .httpTransport(new OkHttpTransport(okHttpClient))
                //.devMode(true)
                .build();
    }

    /**
     * 同步响应
     *
     * @param request
     * @param eventSourceListener
     */
    public ModelApiResponse chat(ModelApiRequest request) {
        request.setInvokeMethod(Constants.invokeMethod);
        // returnType 非必填参数
        request.setReturnType(Constants.RETURN_TYPE_TEXT);
        request.setRequestId(String.format(requestIdTemplate, System.currentTimeMillis()));
        ModelApiResponse invokeModelApiResp = this.clientV3.invokeModelApi(request);
        return invokeModelApiResp;
    }

    /**
     * 流式响应
     *
     * @param request
     * @param eventSourceListener
     */
    public void streamChat(ModelApiRequest request, ModelEventSourceListener eventSourceListener) {
        // 可自定义sse listener
        //request.setSseListener(new StandardEventSourceListener());
        request.setSseListener(eventSourceListener);
        request.setInvokeMethod(Constants.invokeMethodSse);
        // returnType 非必填参数
        request.setReturnType(Constants.RETURN_TYPE_JSON);
        request.setRequestId(String.format(requestIdTemplate, System.currentTimeMillis()));
        this.clientV3.invokeModelApi(request);
    }

    private static ModelApiRequest invokeRequest() {
        ModelApiRequest modelApiRequest = new ModelApiRequest();
        modelApiRequest.setModelId(Constants.ModelChatGLM6BAsync);
        modelApiRequest.setInvokeMethod(Constants.invokeMethod);
        // returnType 非必填参数
        modelApiRequest.setReturnType(Constants.RETURN_TYPE_TEXT);
        ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(ModelConstants.roleUser, "ChatGPT和你哪个更强大");
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        prompts.add(prompt);
        modelApiRequest.setPrompt(prompts);
        // 关闭搜索示例
        //  modelApiRequest.setRef(new HashMap<String, Object>(){{
        //    put("enable",false);
        // }});
        // 开启搜索示例
        // modelApiRequest.setRef(new HashMap<String, Object>(){{
        //    put("enable",true);
        //    put("search_query","历史");
        //  }});
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        modelApiRequest.setRequestId(requestId);
        return modelApiRequest;
    }


    /**
     * 创建默认的OkHttpClient
     */
    private OkHttpClient okHttpClient(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new ZhiPuLogger());
        httpLoggingInterceptor.setLevel(level);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(300, TimeUnit.SECONDS)//连接超时
                .writeTimeout(300, TimeUnit.SECONDS)//写入超时
                .readTimeout(300, TimeUnit.SECONDS)//读取超时
                .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(200); //设置最大并发请求数，避免等待延迟
        okHttpClient.dispatcher().setMaxRequests(200);
        return okHttpClient;
    }

    /**
     * 构造
     *
     * @return
     */
    public static ZhiPuClient.Builder builder() {
        return new ZhiPuClient.Builder();
    }

    public static final class Builder {
        private @NotNull String appKey;
        private @NotNull String appSecret;

        private OkHttpClient okHttpClient;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder() {
        }


        public ZhiPuClient.Builder appKey(@NotNull String val) {
            appKey = val;
            return this;
        }

        public ZhiPuClient.Builder appSecret(@NotNull String val) {
            appSecret = val;
            return this;
        }

        public ZhiPuClient.Builder logLevel(HttpLoggingInterceptor.Level val) {
            logLevel = val;
            return this;
        }

        public ZhiPuClient.Builder okHttpClient(OkHttpClient val) {
            okHttpClient = val;
            return this;
        }

        public ZhiPuClient build() {
            return new ZhiPuClient(this);
        }
    }

}
