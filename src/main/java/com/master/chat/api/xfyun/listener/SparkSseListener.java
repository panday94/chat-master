package com.master.chat.api.xfyun.listener;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.chat.api.base.entity.ChatData;
import com.master.chat.api.base.enums.ChatContentEnum;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.xfyun.constant.SparkStatusConstant;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.entity.response.SparkResponse;
import com.master.chat.api.xfyun.entity.response.SparkResponseChoices;
import com.master.chat.api.xfyun.entity.response.SparkResponseHeader;
import com.master.chat.api.xfyun.entity.response.SparkResponseUsage;
import com.master.chat.api.xfyun.exception.SparkException;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.service.IChatMessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 讯飞星火回答监听 流式输出
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
public class SparkSseListener extends WebSocketListener {
    private static final String FINISH = "[finish]";
    private final StringBuilder output = new StringBuilder();
    public ObjectMapper objectMapper;
    private SparkRequest request;
    private HttpServletResponse response;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason;
    private String version;
    private Boolean error;
    private String errTxt;

    public SparkSseListener(SparkRequest request, HttpServletResponse response, Long chatId, String parentMessageId, String version) {
        objectMapper = new ObjectMapper();
        // 排除值为null的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.request = request;
        this.response = response;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.version = version;
        this.error = false;
    }

    @Override
    public final void onOpen(@NotNull WebSocket webSocket, @NotNull Response rs) {
        log.info("先发送数据");
        // 发送消息
        String requestJson;
        try {
            requestJson = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new SparkException(400, "请求数据 SparkRequest 序列化失败", e);
        }
        webSocket.send(requestJson);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
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

        this.onMessage(content, usage, status, request, sparkResponse, webSocket);

        // 最后一条结果，关闭连接
        if (SparkStatusConstant.FINISH.equals(status)) {
            webSocket.close(1000, "");
            countDownLatch.countDown();
        }
    }

    public void onMessage(String content, SparkResponseUsage usage, Integer status, SparkRequest sparkRequest, SparkResponse sparkResponse, WebSocket webSocket) {
        log.info("讯飞星火返回：{}", JSON.toJSONString(sparkResponse));
        output.append(content);
        try {
            if (ValidatorUtil.isNull(conversationId) && ValidatorUtil.isNotNull(sparkResponse.getHeader().getSid())) {
                conversationId = sparkResponse.getHeader().getSid();
            }
            String text = output.toString();
            ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                    .parentMessageId(parentMessageId)
                    .role(ChatRoleEnum.ASSISTANT.getValue()).content(text).build();
            response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("消息错误", e);
            countDownLatch.countDown();
        } finally {
            if (SparkStatusConstant.FINISH.equals(status)) {
                log.info("科大讯飞返回数据结束了");
                ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                        .model(ChatModelEnum.SPARK.getValue()).modelVersion(version)
                        .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(FINISH)
                        .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(usage.getText().getTotalTokens().longValue())
                        .build();
                com.master.chat.common.utils.ApplicationContextUtil.getBean(IChatMessageService.class).saveChatMessage(chatMessage);
            }
        }
    }

    @SneakyThrows
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        log.error("讯飞星火连接异常，异常：{}", t.getMessage());
        ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                .parentMessageId(parentMessageId)
                .role(ChatRoleEnum.ASSISTANT.getValue()).content("讯飞星火接口请求失败，无法响应！").contentType(ChatContentEnum.TEXT.getValue()).build();
        this.error = true;
        this.errTxt = "讯飞星火接口连接异常";
        this.response.getWriter().write(JSON.toJSONString(chatData));
        this.response.getWriter().flush();
        super.onFailure(webSocket, t, response);
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return this.countDownLatch;
    }


    public Boolean getError() {
        return error;
    }

    public String getErrTxt() {
        return errTxt;
    }

}
