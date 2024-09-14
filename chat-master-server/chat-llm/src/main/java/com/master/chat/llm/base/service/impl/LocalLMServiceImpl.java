package com.master.chat.llm.base.service.impl;

import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.model.dto.ModelDTO;
import com.master.chat.client.service.GptService;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.locallm.LocalLMClient;
import com.master.chat.llm.locallm.SSEListener;
import com.master.chat.llm.locallm.chatchat.constant.ApiConstant;
import com.master.chat.llm.locallm.chatchat.entity.ChatCompletion;
import com.master.chat.llm.locallm.chatchat.entity.ChatCompletionMessage;
import com.master.chat.llm.locallm.chatchat.enums.ModelEnum;
import com.master.chat.llm.locallm.enums.ModelTypeEnum;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地模型 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class LocalLMServiceImpl implements ModelService {
    private static LocalLMClient localLMClient;
    private static GptService gptService;

    @Autowired
    public LocalLMServiceImpl(LocalLMClient localLMClient, GptService gptService) {
        LocalLMServiceImpl.localLMClient = localLMClient;
        LocalLMServiceImpl.gptService = gptService;
    }

    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        return null;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isWs, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {

        List<ChatCompletionMessage> history = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            ChatCompletionMessage message = ChatCompletionMessage.builder().content(v.getContent()).role(v.getRole()).build();
            history.add(message);
        });
        ModelDTO model = gptService.getModel(ChatModelEnum.LOCALLM.getValue());
        String modelVersion = ValidatorUtil.isNotNull(version) ? version : ApiConstant.DEFAULT_MODEL;
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ModelTypeEnum.getEnum(model.getLocalModelType()), version, model.getKnowledge(), prompt, uid, isWs);
        ChatCompletion chat = ChatCompletion
                .builder()
                .query(prompt)
                .knowledgeBaseName(ApiConstant.DEFAULT_KNOWLEDGE)
                .history(history)
                .modelName(modelVersion)
                .build();
        String domain = ValidatorUtil.isNotNull(model.getModelUrl()) ? model.getModelUrl() : ApiConstant.BASE_DOMAIN;
        ModelEnum modelUrl = ValidatorUtil.isNotNull(model.getKnowledge()) ? ModelEnum.KNOWLEDGE : ModelEnum.LLM;
        localLMClient.streamChat(chat, sseListener, domain, modelUrl);
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

}
