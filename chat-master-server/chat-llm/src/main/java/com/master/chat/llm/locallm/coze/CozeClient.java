package com.master.chat.llm.locallm.coze;

import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.model.dto.ModelDTO;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.locallm.LocalLMClient;
import com.master.chat.llm.locallm.coze.constant.ApiConstant;
import com.master.chat.llm.locallm.coze.entity.ChatCompletion;
import com.master.chat.llm.locallm.coze.entity.ChatCompletionMessage;
import com.master.chat.llm.locallm.coze.enums.ContentTypeEnum;
import com.master.chat.llm.locallm.coze.enums.ModelEnum;
import com.master.chat.llm.locallm.coze.listener.SSEListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Ollama client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * https://www.coze.cn/docs/developer_guides/chat_v3
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@Service
public class CozeClient {
    private static LocalLMClient localLMClient;

    @Autowired
    public CozeClient(LocalLMClient localLMClient) {
        CozeClient.localLMClient = localLMClient;
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
        List<ChatCompletionMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            ChatCompletionMessage message = ChatCompletionMessage.builder().role(v.getRole()).content(v.getContent()).contentType(ContentTypeEnum.TEXT.getValue()).build();
            messages.add(message);
        });
        ChatCompletion chat = ChatCompletion.builder()
                .botId(modelDTO.getKnowledge()).userId(uid).additionalMessages(messages)
                .build();
        String domain = ValidatorUtil.isNotNull(modelDTO.getModelUrl()) ? modelDTO.getModelUrl() : ApiConstant.BASE_DOMAIN;
        ModelEnum modelEnum = ValidatorUtil.isNotNull(modelDTO.getKnowledge()) ? ModelEnum.LLM : ModelEnum.LLM;
        Response callResponse = localLMClient.streamChat(chat, domain, modelEnum.getUrl());
        if (callResponse == null || callResponse.code() != 200) {
            log.error("coze流式响应失败: " + callResponse.body().string());
            return true;
        }
        SSEListener sseListener = new SSEListener(response, chatId, conversationId, version, uid, isWs);
        return sseListener.streamChat(callResponse);
    }

}

