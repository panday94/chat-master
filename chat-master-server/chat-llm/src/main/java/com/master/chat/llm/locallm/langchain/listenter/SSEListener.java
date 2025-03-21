package com.master.chat.llm.locallm.langchain.listenter;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
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
import com.master.chat.llm.locallm.langchain.entity.ChatResponse;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * langchain 流式监听处理
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
    private static final String FINISH = "[finish]";
    private long tokens;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private HttpServletResponse response;
    private SseEmitter sseEmitter;
    private StringBuffer output = new StringBuffer();
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason;
    private String version;
    private String knowledge;
    private Boolean error;
    private String errTxt;
    private String uid;
    private Boolean isWs = false;
    private String prompt;
    private List<String> docs;

    public SSEListener(HttpServletResponse response, SseEmitter sseEmitter, Long chatId, String parentMessageId, String version, String knowledge, String prompt, String uid, Boolean isWs) {
        this.response = response;
        this.sseEmitter = sseEmitter;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.version = version;
        this.knowledge = knowledge;
        this.isWs = isWs;
        this.uid = uid;
        this.error = false;
        this.prompt = prompt;
        if (response == null) {
            log.error("客户端非sse推送");
            return;
        }
        if (!isWs) {
            this.response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        log.info("langchain建立sse连接...");
    }

    /**
     * 流式回答
     *
     * @param request
     */
    public Boolean streamChat(Response response) {
        ChatResponse chatMessageAccumulator = mapStreamToAccumulator(response)
                .doOnNext(accumulator -> {
                    if (accumulator!= null) {
                        String content = ValidatorUtil.isNull(knowledge) ? handleLangchain(accumulator) : handleLangchainKnowledge(accumulator);
                        log.info("langchain返回，数据：{}", content);
                        output.append(content).toString();
                        // 向客户端发送信息
                        output();
                    }
                }).doOnComplete(System.out::println).lastElement().blockingGet();
        this.conversationId = chatMessageAccumulator.getMessageId();
        log.info("langchain返回数据结束了:{}", JSON.toJSONString(chatMessageAccumulator));
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

    public static Flowable<ChatResponse> mapStreamToAccumulator(Response response) {
        return Flowable.create(emitter -> {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                emitter.onError(new RuntimeException("Response body is null"));
                return;
            }
            String line;
            while ((line = responseBody.source().readUtf8Line()) != null) {
                if (line.startsWith("data:")) {
                    line = line.substring(5);
                    line = line.trim();
                }
                if (Objects.equals(line, "[DONE]")) {
                    emitter.onComplete();
                    return;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Gson gson = new Gson();
                ChatResponse streamResponse = gson.fromJson(line, ChatResponse.class);
                emitter.onNext(streamResponse);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 处理Langchain流式返回
     *
     * @return
     */
    private String handleLangchain(ChatResponse chatResponse) {
        conversationId = chatResponse.getMessageId();
        output.append(chatResponse.getText()).toString();
        return output.toString();
    }

    /**
     * 处理Langchain知识库流式返回
     *
     * @return
     */
    private String handleLangchainKnowledge(ChatResponse chatResponse) {
        if (ValidatorUtil.isNotNullIncludeArray(chatResponse.getDocs())) {
            tokens = output.toString().length() + prompt.length();
            finishReason = FINISH;
            conversationId = UUID.fastUUID().toString();
            docs = chatResponse.getDocs();
            return FINISH;
        }
        String content = chatResponse.getAnswer();
        output.append(content).toString();
        return output.toString();
    }

}
