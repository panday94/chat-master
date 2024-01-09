package com.master.chat.api.xfyun;

import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.entity.SparkSyncChatResponse;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.exception.SparkException;
import com.master.chat.api.xfyun.listener.SparkBaseListener;
import com.master.chat.api.xfyun.listener.SparkSyncChatListener;
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
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class SparkClient {

    public String appid;

    public String apiKey;

    public String apiSecret;

    public OkHttpClient client = new OkHttpClient.Builder().build();

    public SparkClient() {
    }

    public SparkClient(String appid, String apiKey, String apiSecret) {
        this.appid = appid;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /**
     * @param sparkRequest
     * @return
     */
    public SparkSyncChatResponse chat(SparkRequest sparkRequest) {
        SparkSyncChatResponse chatResponse = new SparkSyncChatResponse();
        chatResponse.setSuccess(true);
        SparkSyncChatListener syncChatListener = new SparkSyncChatListener(chatResponse);
        this.chatSync(sparkRequest, syncChatListener);
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
     * 同步输出
     *
     * @param sparkRequest
     * @param listener
     */
    private void chatSync(SparkRequest sparkRequest, SparkBaseListener listener) {
        sparkRequest.getHeader().setAppId(appid);
        listener.setSparkRequest(sparkRequest);

        SparkApiVersion apiVersion = sparkRequest.getApiVersion();
        String apiUrl = apiVersion.getUrl();

        // 构建鉴权url
        String authWsUrl;
        try {
            authWsUrl = getAuthUrl(apiUrl).replace("http://", "ws://").replace("https://", "wss://");
        } catch (Exception e) {
            throw new SparkException(500, "构建鉴权url失败", e);
        }
        // 创建请求
        Request request = new Request.Builder().url(authWsUrl).build();
        // 发送请求
        client.newWebSocket(request, listener);
    }

    /**
     * 流式输出
     *
     * @param sparkRequest
     * @param listener
     */
    public void streamChat(SparkRequest sparkRequest, WebSocketListener listener) {
        SparkApiVersion apiVersion = sparkRequest.getApiVersion();
        String apiUrl = apiVersion.getUrl();
        // 构建鉴权url
        String authWsUrl;
        try {
            authWsUrl = getAuthUrl(apiUrl).replace("http://", "ws://").replace("https://", "wss://");
        } catch (Exception e) {
            throw new SparkException(500, "构建鉴权url失败", e);
        }
        // 创建请求
        Request request = new Request.Builder().url(authWsUrl).build();
        // 发送请求
        client.newWebSocket(request, listener);
    }

    /**
     * 获取认证之后的URL
     */
    public String getAuthUrl(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
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
        return httpUrl.toString();
    }

}
