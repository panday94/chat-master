package com.master.chat.llm.base.service.impl;

import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.model.dto.ModelDTO;
import com.master.chat.client.service.GptService;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.locallm.coze.CozeClient;
import com.master.chat.llm.locallm.enums.ModelTypeEnum;
import com.master.chat.llm.locallm.langchain.LangchainClient;
import com.master.chat.llm.locallm.ollama.OllamaClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
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
    private static LangchainClient langchainClient;
    private static OllamaClient ollamaClient;
    private static CozeClient cozeClient;
    private static GptService gptService;

    @Autowired
    public LocalLMServiceImpl(LangchainClient langchainClient, OllamaClient ollamaClient, CozeClient cozeClient, GptService gptService) {
        LocalLMServiceImpl.langchainClient = langchainClient;
        LocalLMServiceImpl.ollamaClient = ollamaClient;
        LocalLMServiceImpl.cozeClient = cozeClient;
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
        ModelDTO modelDTO = gptService.getModel(ChatModelEnum.LOCALLM.getValue());
        ModelTypeEnum modelType = ModelTypeEnum.getEnum(modelDTO.getLocalModelType());
        switch (modelType) {
            case LANGCHAIN:
                return langchainClient.buildChatCompletion(response, sseEmitter, chatId, conversationId, isWs, uid, chatMessages, prompt, version, modelDTO);
            case OLLAMA:
                return ollamaClient.buildChatCompletion(response, sseEmitter, chatId, conversationId, isWs, uid, chatMessages, prompt, version, modelDTO);
            case COZE:
                return cozeClient.buildChatCompletion(response, sseEmitter, chatId, conversationId, isWs, uid, chatMessages, prompt, version, modelDTO);
            default:
                throw new BusinessException("未知的模型类型，功能未接入");
        }
    }


}
