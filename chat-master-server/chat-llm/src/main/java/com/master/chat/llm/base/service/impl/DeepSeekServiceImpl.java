package com.master.chat.llm.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.deepseek.DeepSeekClient;
import com.master.chat.llm.deepseek.enmus.Model;
import com.master.chat.llm.deepseek.sse.SSEListener;
import com.master.chat.llm.openai.entity.chat.ChatChoice;
import com.master.chat.llm.openai.entity.chat.ChatCompletion;
import com.master.chat.llm.openai.entity.chat.ChatCompletionResponse;
import com.master.chat.llm.openai.entity.chat.Message;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * DeepSeek 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Service
public class DeepSeekServiceImpl implements ModelService {
    private static DeepSeekClient deepSeekClient;

    public DeepSeekServiceImpl(DeepSeekClient deepSeekClient) {
        DeepSeekServiceImpl.deepSeekClient = deepSeekClient;
    }

    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        if (ValidatorUtil.isNull(deepSeekClient)) {
            throw new BusinessException("DeepSeek无有效token，请切换其他模型进行聊天");
        }
        List<Message> openAiMessages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            Message currentMessage = Message.builder().content(v.getContent()).role(v.getRole()).build();
            openAiMessages.add(currentMessage);
        });
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(openAiMessages)
                .model(ValidatorUtil.isNotNull(version) ? version : Model.CHAT.getName())
                .build();
        ChatCompletionResponse response;
        try {
            response = deepSeekClient.chatCompletion(chatCompletion);
        } catch (Exception e) {
            throw new LLMException("DeepSeek接口请求异常，请稍后再试");
        }
        ChatChoice choice = response.getChoices().get(0);
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getId())
                .model(ChatModelEnum.DEEPSEEK.getValue()).modelVersion(response.getModel())
                .content(choice.getMessage().getContent()).role(choice.getMessage().getRole()).finishReason(choice.getFinishReason())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(deepSeekClient.getApiKey().get(0)).usedTokens(response.getUsage().getTotalTokens())
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isWs, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNullIncludeArray(deepSeekClient.getApiKey())) {
            throw new BusinessException("未加载到密钥信息");
        }
        List<Message> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            Message currentMessage = Message.builder().content(v.getContent()).role(v.getRole()).build();
            messages.add(currentMessage);
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.DEEPSEEK.getValue(), version, uid, isWs);
        ChatCompletion completion = ChatCompletion
                .builder()
                .messages(messages)
                .model(ValidatorUtil.isNotNull(version) ? version : Model.CHAT.getName())
                .build();
        deepSeekClient.streamChatCompletion(completion, sseListener);
        if (isWs) {
            return false;
        }
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

}
