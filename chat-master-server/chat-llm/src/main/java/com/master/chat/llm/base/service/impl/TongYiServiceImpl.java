package com.master.chat.llm.base.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.tongyi.TongYiClient;
import com.master.chat.llm.tongyi.listener.SSEListener;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 通义 接口实现
 * 文档地址 https://help.aliyun.com/zh/dashscope/developer-reference/api-details?spm=a2c4g.11186623.0.0.234374fakjtImN
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class TongYiServiceImpl implements ModelService {
    private static TongYiClient tongYiClient;

    @Autowired
    public TongYiServiceImpl(TongYiClient tongYiClient) {
        TongYiServiceImpl.tongYiClient = tongYiClient;
    }

    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(20);
        chatMessages.stream().forEach(v -> {
            msgManager.add(Message.builder().role(v.getRole()).content(v.getContent()).build());
        });
        QwenParam param = QwenParam.builder().apiKey(tongYiClient.getAppKey())
                .model(ValidatorUtil.isNotNull(version) ? version : Generation.Models.QWEN_TURBO)
                .messages(msgManager.get())
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .topP(0.3)
                .enableSearch(true)
                .build();
        try {
            GenerationResult response = gen.call(param);
            List<GenerationOutput.Choice> choices = response.getOutput().getChoices();
            ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getRequestId())
                    .model(ChatModelEnum.TONGYI.getValue()).modelVersion(param.getModel())
                    .content(choices.get(0).getMessage().getContent()).finishReason(choices.get(0).getFinishReason())
                    .role(ChatRoleEnum.ASSISTANT.getValue()).status(ChatStatusEnum.SUCCESS.getValue())
                    .appKey(param.getApiKey()).usedTokens(Long.valueOf(response.getUsage().getInputTokens() + response.getUsage().getOutputTokens()))
                    .response(JSON.toJSONString(response))
                    .build();
            return chatMessage;
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            e.printStackTrace();
            throw new LLMException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNull(tongYiClient.getAppKey())) {
            throw new BusinessException("未加载到密钥信息");
        }
        MessageManager msgManager = new MessageManager(20);
        chatMessages.stream().forEach(v -> {
            msgManager.add(Message.builder().role(v.getRole()).content(v.getContent()).build());
        });
        Generation gen = new Generation();
        QwenParam param = QwenParam.builder().apiKey(tongYiClient.getAppKey())
                .model(ValidatorUtil.isNotNull(version) ? version : Generation.Models.QWEN_TURBO)
                .messages(msgManager.get())
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .enableSearch(true)
                .build();
        Semaphore semaphore = new Semaphore(0);
        SSEListener sseListener = new SSEListener(response, semaphore, chatId, conversationId, uid, version);
        gen.streamCall(param, sseListener);
        semaphore.acquire();
        return sseListener.getError();
    }

}
