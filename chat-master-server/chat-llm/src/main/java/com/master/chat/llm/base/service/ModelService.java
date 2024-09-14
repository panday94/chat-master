package com.master.chat.llm.base.service;

import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 大模型接口
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface ModelService {

    /**
     * 同步响应
     *
     * @param chatId
     * @param version
     * @param chatMessages
     * @return
     */
    ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version);

    /**
     * 流式响应
     *
     * @return
     */
    Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isWs, Boolean isDraw,
                       Long chatId, String conversationId, String prompt, String version, String uid);

}
