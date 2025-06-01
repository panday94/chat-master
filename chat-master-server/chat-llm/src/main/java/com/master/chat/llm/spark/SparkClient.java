package com.master.chat.llm.spark;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.key.KeyUpdater;
import com.master.chat.llm.spark.entity.request.ChatRequest;
import com.master.chat.llm.spark.entity.response.ChatSyncResponse;
import com.master.chat.llm.spark.enums.ModelEnum;
import com.master.chat.llm.spark.listener.SyncListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocketListener;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 讯飞星火SparkClient
 * 文档地址：https://www.xfyun.cn/doc/spark/Web.html#_1-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Slf4j
@Data
public class SparkClient implements KeyUpdater {
    public String appid;
    public String apiKey;
    public String apiSecret;
    public OkHttpClient client;

    public SparkClient() {
    }

    public SparkClient(Builder builder) {
        if (StrUtil.isBlank(builder.appKey)) {
            throw new ValidateException("构造错误: apiKey不能为空");
        }
        this.appid = builder.appId;
        this.apiKey = builder.appKey;
        this.apiSecret = builder.appSecret;
        this.client = new OkHttpClient.Builder().build();
    }

    /**
     * 同步输出
     *
     * @param chatRequest
     * @return
     */
    public ChatSyncResponse chat(ChatRequest chatRequest) {
        ChatSyncResponse chatResponse = new ChatSyncResponse();
        chatResponse.setSuccess(true);
        SyncListener syncListener = new SyncListener(chatResponse);
        chatRequest.getHeader().setAppId(appid);
        syncListener.setSparkRequest(chatRequest);
        Request request;
        try {
            request = getRequest(chatRequest.getApiVersion());
        } catch (Exception e) {
            throw new LLMException("构建鉴权url失败");
        }
        // 发送请求
        client.newWebSocket(request, syncListener);
        while (!chatResponse.isOk()) {
            if (!chatResponse.isSuccess()) {
                return chatResponse;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }
        return chatResponse;
    }

    /**
     * 流式输出
     *
     * @param chatRequest
     * @param listener
     */
    public void streamChat(ChatRequest chatRequest, WebSocketListener listener) {
        Request request;
        try {
            request = getRequest(chatRequest.getApiVersion());
        } catch (Exception e) {
            throw new LLMException("构建鉴权url失败");
        }
        // 发送请求
        System.out.println(JSON.toJSONString(chatRequest));
        client.newWebSocket(request, listener);
    }

    /**
     * 获取认证之后的URL
     */
    private Request getRequest(ModelEnum model) throws Exception {
        URL url = new URL(model.getUrl());
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);

        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).
                addQueryParameter("date", date).
                addQueryParameter("host", url.getHost()).
                build();
        // 创建请求
        return new Request.Builder().url(httpUrl.toString().replace("http://", "ws://").replace("https://", "wss://")).build();
    }

    @Override
    public String supportModel() {
        return ChatModelEnum.SPARK.getValue();
    }

    @Override
    public void updateKey(KeyModel keyModel) {
        this.setAppid(keyModel.getAppId());
        this.setApiSecret(keyModel.getAppSecret());
        this.setApiKey(keyModel.getAppKey());
    }

    /**
     * 构造
     *
     * @return
     */
    public static SparkClient.Builder builder() {
        return new SparkClient.Builder();
    }

    public static final class Builder {
        private String appId;
        private String appKey;
        private String appSecret;

        public Builder() {
        }

        public SparkClient.Builder appId(String val) {
            appId = val;
            return this;
        }

        public SparkClient.Builder appKey(String val) {
            appKey = val;
            return this;
        }

        public SparkClient.Builder appSecret(String val) {
            appSecret = val;
            return this;
        }

        public SparkClient build() {
            return new SparkClient(this);
        }
    }

}
