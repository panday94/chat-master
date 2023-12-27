package com.master.chat.api.qianwen.listener;

import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.fastjson.JSON;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.base.entity.ChatData;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.common.utils.ApplicationContextUtil;
import com.master.common.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Semaphore;

/**
 * 通义千问监听sse流式响应处理
 *
 * @author: yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
 */
@Slf4j
public class QianWenSseListener extends ResultCallback<GenerationResult> {
    private Semaphore semaphore;
    private HttpServletResponse response;
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason;
    private String version;

    public QianWenSseListener(HttpServletResponse response, Semaphore semaphore, Long chatId, String parentMessageId, String version) {
        this.response = response;
        this.semaphore = semaphore;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        this.response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        this.response.setStatus(HttpStatus.OK.value());
        this.version = version;
    }

    @Override
    public void onEvent(GenerationResult result) {
        log.error("通义千问SSE返回，数据：{}", JSON.toJSONString(result));
        GenerationOutput.Choice choice = result.getOutput().getChoices().get(0);
        if (ValidatorUtil.isNotNull(choice.getFinishReason())) {
            finishReason = choice.getFinishReason();
            log.info("通义千问返回数据结束了");
            ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(result.getRequestId()).parentMessageId(parentMessageId)
                    .model(ChatModelEnum.QIANWEN.getValue()).modelVersion(version)
                    .content(choice.getMessage().getContent()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                    .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(Long.valueOf(result.getUsage().getOutputTokens() + result.getUsage().getInputTokens()))
                    .build();
            ApplicationContextUtil.getBean(IChatMessageService.class).saveChatMessage(chatMessage);
            return;
        }
        String text = choice.getMessage().getContent();
        ChatData chatData = ChatData.builder().id(result.getRequestId()).conversationId(result.getRequestId())
                .parentMessageId(parentMessageId)
                .role(ChatRoleEnum.ASSISTANT.getValue()).text(text).build();
        try {
            response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("通义千问输出失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onComplete() {
        log.error("通义千问输出完成");
        semaphore.release();
    }

    @Override
    public void onError(Exception e) {
        log.error("通义千问输出失败");
        semaphore.release();
    }

}
