package com.master.chat.llm.base.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.spark.SparkClient;
import com.master.chat.llm.spark.entity.request.ChatCompletionMessage;
import com.master.chat.llm.spark.entity.request.ChatRequest;
import com.master.chat.llm.spark.entity.response.ChatSyncResponse;
import com.master.chat.llm.spark.entity.response.Usage;
import com.master.chat.llm.spark.enums.ModelEnum;
import com.master.chat.llm.spark.listener.SSEListener;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 讯飞星火 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class SparkServiceImpl implements ModelService {
    private static SparkClient sparkClient;

    public SparkServiceImpl(SparkClient SparkClient) {
        SparkServiceImpl.sparkClient = SparkClient;
    }

    @Override
    @SneakyThrows
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        if (ValidatorUtil.isNull(sparkClient)) {
            throw new BusinessException("讯飞星火无有效token，请切换其他模型进行聊天");
        }
        List<ChatCompletionMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            ChatCompletionMessage currentMessage = new ChatCompletionMessage(v.getRole(), v.getContent());
            messages.add(currentMessage);
        });
        // 构造请求
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.5)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(ValidatorUtil.isNotNull(version) ? ModelEnum.getEnum(version) : ModelEnum.V2_0)
                .chatId(chatId.toString())
                .build();
        ChatSyncResponse response = sparkClient.chat(chatRequest);
        if (!response.isSuccess()) {
            throw new LLMException(response.getErrTxt());
        }
        Usage textUsage = response.getTextUsage();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(UUID.fastUUID().toString())
                .model(ChatModelEnum.SPARK.getValue()).modelVersion(ModelEnum.V2_0.getVersion())
                .content(response.getContent()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(sparkClient.apiKey).usedTokens(Long.valueOf(textUsage.getTotalTokens()))
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isWs, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNull(sparkClient.appid)) {
            throw new BusinessException("未加载到密钥信息");
        }
        List<ChatCompletionMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            ChatCompletionMessage currentMessage = new ChatCompletionMessage(v.getRole(), v.getContent());
            messages.add(currentMessage);
        });
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.5)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(ValidatorUtil.isNotNull(version) ? ModelEnum.getEnum(version) : ModelEnum.V2_0)
                .chatId(chatId.toString())
                .build();
        chatRequest.getHeader().setAppId(sparkClient.appid);
        SSEListener sseListener = new SSEListener(chatRequest, response, chatId, conversationId, uid, version, isWs);
        sparkClient.streamChat(chatRequest, sseListener);
        if (isWs) {
            return false;
        }
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

}
