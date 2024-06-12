package com.master.chat.llm.base.service.impl;

import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.internlm.InternlmClient;
import com.master.chat.llm.internlm.constant.ModelConstant;
import com.master.chat.llm.internlm.entity.request.ChatCompletion;
import com.master.chat.llm.internlm.entity.request.ChatCompletionMessage;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 书生浦语 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class InternLMServiceImpl implements ModelService {
    private static InternlmClient internlmClient;

    @Autowired
    public InternLMServiceImpl(InternlmClient internlmClient) {
        InternLMServiceImpl.internlmClient = internlmClient;
    }

    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        return null;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNull(internlmClient.getToken())) {
            throw new BusinessException("未加载到密钥信息");
        }
        List<ChatCompletionMessage> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            messages.add(new ChatCompletionMessage(v.getRole(), v.getContent()));
        });
        String modelVaersion = ValidatorUtil.isNotNull(version) ? version : ModelConstant.LATEST;
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(modelVaersion)
                .messages(messages)
                .build();
        return internlmClient.streamChat(response, chatCompletion, chatId, conversationId, modelVaersion, uid);
    }

}
