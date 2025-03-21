package com.master.chat.llm.locallm.dify.listener;

import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatContentEnum;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.service.GptService;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.framework.util.ApplicationContextUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.entity.ChatData;
import com.master.chat.llm.base.websocket.WebsocketServer;
import com.master.chat.llm.base.websocket.constant.FunctionCodeConstant;
import com.master.chat.llm.base.websocket.entity.WebSocketData;
import com.master.chat.llm.locallm.dify.entity.ChatResponse;
import com.master.chat.llm.locallm.dify.enums.MessageTypeEnum;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * coze 流式监听处理
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
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
    private Boolean isCompleted = false;
    private List<String> docs;
    private List<String> followUps;

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
        this.docs = new ArrayList<>();
        this.followUps = new ArrayList<>();
        if (response == null) {
            log.error("客户端非sse推送");
            return;
        }
        if (!isWs) {
            response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        log.info("coze建立sse连接...");
    }

    /**
     * 流失回答
     *
     * @param request
     */
    public Boolean streamChat(Response response) {
        ChatResponse chatMessageAccumulator = mapStreamToAccumulator(response)
                .doOnNext(accumulator -> {
                    if (accumulator.getAnswer() != null) {
                        log.info("dify返回，数据：{}", accumulator.getAnswer());
                        output.append(accumulator.getAnswer()).toString();
                        // 向客户端发送信息
                        output();
                    }
                }).doOnComplete(System.out::println).lastElement().blockingGet();
        this.conversationId = chatMessageAccumulator.getConversationId();
        log.info("dify返回数据结束了:{}", JSON.toJSONString(chatMessageAccumulator));
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                .model(ChatModelEnum.LOCALLM.getValue()).modelVersion(version)
                .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(Long.valueOf(0L))
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

    public Flowable<ChatResponse> mapStreamToAccumulator(Response response) {
        return Flowable.create(emitter -> {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                emitter.onError(new RuntimeException("Response body is null"));
                return;
            }
            String line;
            while ((line = responseBody.source().readUtf8Line()) != null) {
                if (line.equals(StringPoolConstant.EMPTY) || line.equals(StringPoolConstant.NEWLINE)) {
                    continue;
                }
                log.info("Dify返回数据：" + line);
                ChatResponse streamResponse = JSON.parseObject(line.replaceAll("^data: ", "").replaceAll("\n\n$", ""), ChatResponse.class);
                if (streamResponse.getEvent().equals(MessageTypeEnum.ERROR.getValue())) {
                    emitter.onError(new ErrorException("Error: " + streamResponse.getMessage()));
                    return;
                }
                if (streamResponse.getEvent().equals(MessageTypeEnum.MESSAGE_END.getValue()) || streamResponse.getEvent().equals(MessageTypeEnum.TTS_MESSAGE_END.getValue())) {
                    emitter.onComplete();
                    return;
                }
                // 过滤掉非消息事件
                if (!streamResponse.getEvent().equals(MessageTypeEnum.MESSAGE.getValue())) {
                    continue;
                }
                emitter.onNext(streamResponse);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }


}
