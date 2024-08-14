package com.master.chat.llm.locallm;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.master.chat.client.enums.ChatContentEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.service.GptService;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.framework.util.ApplicationContextUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.entity.ChatData;
import com.master.chat.llm.locallm.chatchat.entity.ChatResponse;
import com.master.chat.llm.locallm.chatchat.entity.Glm4Response;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 月之暗面 流式监听处理
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class SSEListener extends EventSourceListener {
    private static final String FINISH = "[finish]";
    private static final String STOP = "stop";
    private long tokens;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private HttpServletResponse response;
    private SseEmitter sseEmitter;
    private StringBuffer output = new StringBuffer();
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason;
    private String model;
    private String version;
    private Boolean error;
    private String errTxt;
    private String prompt;
    private List<String> docs;

    public SSEListener(HttpServletResponse response, SseEmitter sseEmitter, Long chatId, String parentMessageId, String model, String version, String prompt) {
        this.response = response;
        this.sseEmitter = sseEmitter;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.model = model;
        this.version = version;
        this.error = false;
        this.prompt = prompt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onOpen(EventSource eventSource, Response rp) {
        if (response == null) {
            log.error("客户端非sse推送");
            return;
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        log.info("{}建立sse连接...", model);
    }

    /**
     * {@inheritDoc}
     */
//    @SneakyThrows
//    @Override
//    public void onEvent(EventSource eventSource, String id, String type, String data) {
//        log.info("SSE返回，模型：{}，ID：{}，TYPE：{}，数据：{}", model, id, type, data);
//        ChatData chatData;
//        String text;
//        try {
//            switch (model) {
//                case "langchain":
//                    text = handleLangchain(type, data);
//                    break;
//                case "langchain-know":
//                    text = handleLangchainKnowledge(type, data);
//                    break;
//                default:
//                    throw new BusinessException("未知的模型类型，功能未接入");
//            }
//            if (FINISH.equals(text)) {
//                log.info("{}返回数据结束了", model);
//                sseEmitter.complete();
//                ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
//                        .model(model).modelVersion(version)
//                        .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
//                        .status(ChatStatusEnum.SUCCESS.getValue()).usedTokens(tokens)
//                        .build();
//                ApplicationContextUtil.getBean(GptService.class).saveChatMessage(chatMessage);
//                return;
//            }
//            chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
//                    .parentMessageId(parentMessageId)
//                    .role(ChatRoleEnum.ASSISTANT.getValue()).content(text).build();
//            response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
//            response.getWriter().flush();
//        } catch (Exception e) {
//            log.error("消息错误", e);
//            eventSource.cancel();
//            countDownLatch.countDown();
//            throw new ErrorException();
//        }
//    }


    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("SSE返回，模型：{}，ID：{}，TYPE：{}，数据：{}", model, id, type, data);
        ChatData chatData;
        String text;
        try {
            switch (model) {
                case "langchain":
                    text = handleLangchain1(type, data);
                    break;
                case "langchain-know":
                    text = handleLangchainKnowledge(type, data);
                    break;
                default:
                    throw new BusinessException("未知的模型类型，功能未接入");
            }
            if (STOP.equals(text)) {
                log.info("{}返回数据结束了", model);
                sseEmitter.complete();
                ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                        .model(model).modelVersion(version)
                        .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                        .status(ChatStatusEnum.SUCCESS.getValue()).usedTokens(tokens)
                        .build();
                ApplicationContextUtil.getBean(GptService.class).saveChatMessage(chatMessage);
                return;
            }
            chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                    .parentMessageId(parentMessageId)
                    .role(ChatRoleEnum.ASSISTANT.getValue()).content(text).build();
            response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("消息错误", e);
            eventSource.cancel();
            countDownLatch.countDown();
            throw new ErrorException();
        }
    }
    /**
     * 处理Langchain流式返回
     *
     * @return
     */
    private String handleLangchain1(String type, String data) {
        Glm4Response chatResponse = JSON.parseObject(data, Glm4Response.class);
        List<JSONObject> choices = chatResponse.getChoices();
        String finish_reason= "";
        finish_reason = choices.get(0).getString("finish_reason");

        if (finish_reason != null && finish_reason.equals("stop")) {
            return "stop";
        }else{
            JSONObject delta = choices.get(0).getJSONObject("delta");

            String role = delta.getString("role");
            if (role.equals("assistant")) {
                String content = delta.getString("content");
                output.append(content).toString();
            }
            return output.toString();
        }

    }

    /**
     * 处理Langchain流式返回
     *
     * @return
     */
    private String handleLangchain(String type, String data) {
        ChatResponse chatResponse = JSON.parseObject(data, ChatResponse.class);
        conversationId = chatResponse.getMessageId();
        output.append(chatResponse.getText()).toString();
        return output.toString();
    }

    /**
     * 处理Langchain知识库流式返回
     *
     * @return
     */
    private String handleLangchainKnowledge(String type, String data) {
        ChatResponse chatResponse = JSON.parseObject(data, ChatResponse.class);
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


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("{}关闭sse连接，流式输出返回值总共{}tokens", model, tokens());
        tokens = output.toString().length() + prompt.length();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                .model(model).modelVersion(version)
                .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                .status(ChatStatusEnum.SUCCESS.getValue()).usedTokens(tokens)
                .build();
        ApplicationContextUtil.getBean(GptService.class).saveChatMessage(chatMessage);
        eventSource.cancel();
        countDownLatch.countDown();
    }

    @SneakyThrows
    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        if (ValidatorUtil.isNotNull(response) && Objects.nonNull(response.body())) {
            log.error("sse连接异常data.body：{}，异常：{}", response.body().string(), t);
        } else {
            log.error("sse连接异常data：{}，异常：{}", response, t);
        }
        ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                .parentMessageId(parentMessageId)
                .role(ChatRoleEnum.ASSISTANT.getValue()).content("AI大模型接口请求失败，无法响应！").contentType(ChatContentEnum.TEXT.getValue()).build();
        this.error = true;
        this.errTxt = "大模型接口连接异常";
        this.response.getWriter().write(JSON.toJSONString(chatData));
        this.response.getWriter().flush();
        eventSource.cancel();
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return this.countDownLatch;
    }

    /**
     * tokens
     *
     * @return
     */
    public long tokens() {
        return tokens;
    }

    public Boolean getError() {
        return error;
    }

    public String getErrTxt() {
        return errTxt;
    }

}
