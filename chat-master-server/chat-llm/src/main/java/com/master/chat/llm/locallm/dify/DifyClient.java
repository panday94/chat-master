package com.master.chat.llm.locallm.dify;

import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.model.dto.ModelDTO;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.locallm.LocalLMClient;
import com.master.chat.llm.locallm.coze.enums.ContentTypeEnum;
import com.master.chat.llm.locallm.dify.constant.ApiConstant;
import com.master.chat.llm.locallm.dify.entity.ChatCompletion;
import com.master.chat.llm.locallm.dify.enums.ModelEnum;
import com.master.chat.llm.locallm.dify.listener.SSEListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Dify client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * https://www.coze.cn/docs/developer_guides/chat_v3
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@Service
public class DifyClient {
    private static LocalLMClient localLMClient;

    @Autowired
    public DifyClient(LocalLMClient localLMClient) {
        DifyClient.localLMClient = localLMClient;
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
        ChatCompletion chat = ChatCompletion.builder()
                .query(prompt).user(uid).responseMode("streaming").inputs(new HashMap<>())
                .build();
        String domain = ValidatorUtil.isNotNull(modelDTO.getModelUrl()) ? modelDTO.getModelUrl() : ApiConstant.BASE_DOMAIN;
        ModelEnum modelEnum = ValidatorUtil.isNotNull(modelDTO.getKnowledge()) ? ModelEnum.LLM : ModelEnum.LLM;
        Response callResponse = localLMClient.streamChat(chat, domain, modelEnum.getUrl());
        if (callResponse == null || callResponse.code() != 200) {
            log.error("Dify流式响应失败: " + callResponse.body().string());
            return true;
        }
        SSEListener sseListener = new SSEListener(response, chatId, conversationId, version, uid, isWs);
        return sseListener.streamChat(callResponse);
    }

}

