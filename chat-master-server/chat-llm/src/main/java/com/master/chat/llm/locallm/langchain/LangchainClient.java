package com.master.chat.llm.locallm.langchain;

import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.model.dto.ModelDTO;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.locallm.LocalLMClient;
import com.master.chat.llm.locallm.langchain.constant.ApiConstant;
import com.master.chat.llm.locallm.langchain.entity.ChatCompletion;
import com.master.chat.llm.locallm.langchain.entity.ChatCompletionMessage;
import com.master.chat.llm.locallm.langchain.enums.ModelEnum;
import com.master.chat.llm.locallm.langchain.listenter.SSEListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * LangChain-Chatchat client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@Service
public class LangchainClient {
    private static LocalLMClient localLMClient;

    @Autowired
    public LangchainClient(LocalLMClient localLMClient) {
        LangchainClient.localLMClient = localLMClient;
    }

    /**
     * 构建请求
     *
     * @param sseListener
     * @param chatMessages
     * @param prompt
     * @param version
     * @param modelDTO
     */
    @SneakyThrows
    public Boolean buildChatCompletion(HttpServletResponse response, SseEmitter sseEmitter, Long chatId, String conversationId, Boolean isWs, String uid,
                                       List<ChatMessageDTO> chatMessages, String prompt, String version, ModelDTO modelDTO) {
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, version, modelDTO.getKnowledge(), prompt, uid, isWs);
        List<ChatCompletionMessage> history = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            ChatCompletionMessage message = ChatCompletionMessage.builder().content(v.getContent()).role(v.getRole()).build();
            history.add(message);
        });
        String model = ValidatorUtil.isNotNull(version) ? version : ApiConstant.DEFAULT_MODEL;
        ChatCompletion chat = ChatCompletion.builder()
                .query(prompt)
                .knowledgeBaseName(ApiConstant.DEFAULT_KNOWLEDGE)
                .history(history)
                .modelName(model)
                .build();
        String domain = ValidatorUtil.isNotNull(modelDTO.getModelUrl()) ? modelDTO.getModelUrl() : ApiConstant.BASE_DOMAIN;
        ModelEnum modelUrl = ValidatorUtil.isNotNull(modelDTO.getKnowledge()) ? ModelEnum.KNOWLEDGE : ModelEnum.LLM;
        localLMClient.streamChat(chat, sseListener, domain, modelUrl.getUrl());
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

}

