package com.master.chat.llm.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.doubao.DouBaoClient;
import com.master.chat.llm.doubao.enums.ModelEnum;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 豆包 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class DouBaoServiceImpl implements ModelService {
    private static DouBaoClient douBaoClient;

    @Autowired
    public DouBaoServiceImpl(DouBaoClient douBaoClient) {
        DouBaoServiceImpl.douBaoClient = douBaoClient;
    }

    /**
     * 豆包文本对话
     * https://www.volcengine.com/docs/82379/1298454
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        if (ValidatorUtil.isNull(douBaoClient)) {
            throw new BusinessException("未加载到豆包密钥信息");
        }
        List<ChatMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            messages.add(ChatMessage.builder().role(ChatMessageRole.valueOf(v.getRole().toUpperCase())).content(v.getContent()).build());
        });
        String modelVaersion = ValidatorUtil.isNotNull(version) ? version : ModelEnum.LITE.getModel();
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(modelVaersion)
                .messages(messages)
                .build();
        ChatCompletionResult response = douBaoClient.chat(request);
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getId())
                .model(ChatModelEnum.DOUBAO.getValue()).modelVersion(modelVaersion)
                .content(response.getChoices().get(0).getMessage().getContent().toString()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(douBaoClient.getApiKey()).usedTokens(Long.valueOf(response.getUsage().getTotalTokens()))
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isWs, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNull(douBaoClient.getApiKey())) {
            throw new BusinessException("未加载到豆包密钥信息");
        }
        List<ChatMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            messages.add(ChatMessage.builder().role(ChatMessageRole.valueOf(v.getRole().toUpperCase())).content(v.getContent()).build());
        });
        String modelVaersion = ValidatorUtil.isNotNull(version) ? version : ModelEnum.LITE.getModel();
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(modelVaersion)
                .messages(messages)
                .build();
        Boolean flag = douBaoClient.streamChat(response, chatCompletionRequest, chatId, conversationId, modelVaersion, uid, isWs);
        if (isWs) {
            return false;
        }
        return flag;
    }

}
