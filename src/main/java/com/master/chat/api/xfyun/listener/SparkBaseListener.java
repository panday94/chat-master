package com.master.chat.api.xfyun.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.api.xfyun.exception.SparkException;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.entity.response.SparkResponse;
import com.master.chat.api.xfyun.entity.response.SparkResponseChoices;
import com.master.chat.api.xfyun.entity.response.SparkResponseHeader;
import com.master.chat.api.xfyun.entity.response.SparkResponseUsage;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 讯飞星火 监听
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class SparkBaseListener extends WebSocketListener {

    private SparkRequest sparkRequest;

    public ObjectMapper objectMapper;

    public SparkBaseListener() {
        objectMapper = new ObjectMapper();
        // 排除值为null的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public final void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        // 发送消息
        String requestJson = null;
        try {
            requestJson = objectMapper.writeValueAsString(sparkRequest);
        } catch (JsonProcessingException e) {
            throw new SparkException(400, "请求数据 SparkRequest 序列化失败", e);
        }
        webSocket.send(requestJson);
    }

    @Override
    public final void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        SparkResponse sparkResponse;
        // 解析响应
        try {
            sparkResponse = objectMapper.readValue(text, SparkResponse.class);
        } catch (JsonProcessingException e) {
            webSocket.close(1000, "");
            throw new SparkException(500, "响应数据 SparkResponse 解析失败：" + text, e);
        }
        SparkResponseHeader header = sparkResponse.getHeader();
        if (null == header) {
            webSocket.close(1000, "");
            throw new SparkException(500, "响应数据不完整 SparkResponse.header为null，完整响应：" + text);
        }

        // 业务状态判断
        Integer code = header.getCode();
        if (0 != code) {
            webSocket.close(1000, "");
            throw SparkException.bizFailed(code);
        }

        // 回答文本
        SparkResponseChoices choices = sparkResponse.getPayload().getChoices();
        List<SparkMessage> messages = choices.getText();
        StringBuilder stringBuilder = new StringBuilder();
        for (SparkMessage message : messages) {
            stringBuilder.append(message.getContent());
        }
        String content = stringBuilder.toString();

        SparkResponseUsage usage = sparkResponse.getPayload().getUsage();
        Integer status = header.getStatus();

        this.onMessage(content, usage, status, sparkRequest, sparkResponse, webSocket);

        // 最后一条结果，关闭连接
        if (2 == status) {
            webSocket.close(1000, "");
        }
    }

    /**
     * 收到回答时会调用此方法
     *
     * @param content       回答内容
     * @param usage         tokens消耗统计
     * @param status        会话状态，取值为[0,1,2]；0代表首次结果；1代表中间结果；2代表最后一个结果，当status为2时，webSocket连接会自动关闭
     * @param sparkRequest  本次会话的请求参数
     * @param sparkResponse 本次回调的响应数据
     * @param webSocket     本次会话的webSocket连接
     */
    public void onMessage(String content, SparkResponseUsage usage, Integer status, SparkRequest sparkRequest, SparkResponse sparkResponse, WebSocket webSocket) {
        // 重写此方法，实现业务逻辑
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        throw new SparkException(500, "讯飞星火api连接异常：" + t.getMessage(), t);
    }

    public SparkRequest getSparkRequest() {
        return sparkRequest;
    }

    public void setSparkRequest(SparkRequest sparkRequest) {
        this.sparkRequest = sparkRequest;
    }
}
