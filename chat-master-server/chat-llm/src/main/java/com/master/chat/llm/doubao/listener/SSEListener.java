package com.master.chat.llm.doubao.listener;

import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatContentEnum;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.service.GptService;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.framework.util.ApplicationContextUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.entity.ChatData;
import com.master.chat.llm.base.websocket.WebsocketServer;
import com.master.chat.llm.base.websocket.constant.FunctionCodeConstant;
import com.master.chat.llm.base.websocket.entity.WebSocketData;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChunk;
import io.reactivex.Flowable;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 豆包流式接口处理
 *
 * @author: Yang
 * @date: 2023/12/4
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class SSEListener {
    private HttpServletResponse response;
    private StringBuffer output = new StringBuffer();
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason = "stop";
    private String version;
    private Boolean error;
    private String errTxt;
    private String uid;
    private Boolean isWs = false;

    /**
     * 流式响应
     *
     * @param request
     * @param query
     */
    public SSEListener(HttpServletResponse response, Long chatId, String parentMessageId, String version, String uid, Boolean isWs) {
        this.response = response;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.version = version;
        this.uid = uid;
        this.isWs = isWs;
        if (response == null) {
            log.error("客户端非sse推送");
            return;
        }
        if (!isWs) {
            response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        log.info("豆包建立sse连接...");
    }

    /**
     * 流式响应
     *
     * @param request
     */
    public Boolean streamChat(Flowable<ChatCompletionChunk> chunks) {
        ChatCompletionChunk chatCompletionChunk = chunks.doOnError(Throwable::printStackTrace)
                    .doOnNext(
                            choice -> {
                                if (choice.getChoices().size() > 0) {
                                    log.info("豆包返回，数据：{}", choice.getChoices().get(0).getMessage().getContent());
                                    output.append(choice.getChoices().get(0).getMessage().getContent()).toString();
                                    // 向客户端发送信息
                                    output();
                                }
                            }
                    ).doOnComplete(System.out::println)
                    .lastElement()
                    .blockingGet();;
            this.conversationId = chatCompletionChunk.getId();
            log.info("豆包返回数据结束了:{}", JSON.toJSONString(chatCompletionChunk));
            ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                    .model(ChatModelEnum.DOUBAO.getValue()).modelVersion(version)
                    .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                    .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(Long.valueOf(output.toString().length()))
                    .build();
            ApplicationContextUtil.getBean(GptService.class).saveChatMessage(chatMessage);
            return false;

    }

    private void output() {
        try {
            String text = output.toString();
            ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                    .parentMessageId(parentMessageId)
                    .role(ChatRoleEnum.ASSISTANT.getValue()).content(text).build();
            if (isWs) {
                WebSocketData wsData = WebSocketData.builder().functionCode(FunctionCodeConstant.MESSAGE).message(chatData).build();
                WebsocketServer.sendMessageByUserId(uid, JSON.toJSONString(wsData));
            } else {
                response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
                response.getWriter().flush();
            }
        } catch (IOException e) {
            log.error("消息错误", e);
            throw new ErrorException();
        }
    }

}
