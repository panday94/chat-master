package com.master.chat.api.base.listener;

import com.alibaba.fastjson.JSON;
import com.master.chat.api.base.entity.ChatData;
import com.master.chat.api.base.enums.ChatContentEnum;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.openai.entity.chat.ChatCompletionResponse;
import com.master.chat.api.wenxin.entity.ChatResponse;
import com.master.chat.api.zhipu.entity.ZhiPuChoice;
import com.master.chat.api.zhipu.entity.ZhiPuResponse;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.ApplicationContextUtil;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.service.IChatMessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * 流式响应同步返回 监听
 *
 * @author: Yang
 * @date: 2023/9/7
 * @version: 1.0.0
 * Copyright Ⓒ 2023 MasterComputer Corporation Limited All rights reserved.
 */
@Slf4j
public class SSEListener extends EventSourceListener {
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
    private String model;
    private String version;
    private Boolean error;
    private String errTxt;

    public SSEListener(HttpServletResponse response, SseEmitter sseEmitter, Long chatId, String parentMessageId, String model, String version) {
        this.response = response;
        this.sseEmitter = sseEmitter;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.model = model;
        this.version = version;
        this.error = false;
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
    @SneakyThrows
    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        log.info("SSE返回，模型：{}，ID：{}，TYPE：{}，数据：{}", model, id, type, data);
        ChatData chatData;
        String text;
        try {
            ChatModelEnum modelEnum = ChatModelEnum.getEnum(model);
            switch (modelEnum) {
                case CHAT_GPT:
                    text = handleOpenAI(data);
                    break;
                case WENXIN:
                    text = handleWenXin(data);
                    break;
                case ZHIPU:
                    text = handleZhiPu(type, data);
                    break;
                default:
                    throw new BusinessException("未知的模型类型，功能未接入");
            }
            if (FINISH.equals(text)) {
                log.info("{}返回数据结束了", model);
                sseEmitter.complete();
                ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                        .model(model).modelVersion(version)
                        .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                        .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(tokens)
                        .build();
                ApplicationContextUtil.getBean(IChatMessageService.class).saveChatMessage(chatMessage);
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
     * 处理openai流式返回
     *
     * @return
     */
    private String handleOpenAI(String data) {
        tokens += 1;
        if (data.equals("[DONE]")) {
            return FINISH;
        }
        ChatCompletionResponse completionResponse = JSON.parseObject(data, ChatCompletionResponse.class);
        String content = completionResponse.getChoices().get(0).getDelta().getContent();
        if (ValidatorUtil.isNull(conversationId) && ValidatorUtil.isNotNull(completionResponse.getId())) {
            conversationId = completionResponse.getId();
        }
        if (ValidatorUtil.isNotNull(completionResponse.getModel())) {
            version = completionResponse.getModel();
        }
        if (ValidatorUtil.isNull(finishReason) && ValidatorUtil.isNotNull(completionResponse.getChoices().get(0).getFinishReason())) {
            finishReason = completionResponse.getChoices().get(0).getFinishReason();
        }
        if (ValidatorUtil.isNull(content)) {
            content = StringPoolConstant.EMPTY;
        }
        output.append(content).toString();
        return output.toString();
    }

    /**
     * 处理文心一言流式返回
     *
     * @return
     */
    private String handleWenXin(String data) {
        ChatResponse completionResponse = JSON.parseObject(data, ChatResponse.class);
        if (completionResponse.getIs_end()) {
            tokens = completionResponse.getUsage().getTotalTokens();
            return FINISH;
        }
        String content = completionResponse.getResult();
        if (ValidatorUtil.isNull(conversationId) && ValidatorUtil.isNotNull(completionResponse.getId())) {
            conversationId = completionResponse.getId();
        }
        finishReason = FINISH;
        output.append(content).toString();
        return output.toString();
    }

    /**
     * 处理智谱清言流式返回
     *
     * @return
     */
    private String handleZhiPu(String type, String data) {
        ZhiPuResponse completionResponse = JSON.parseObject(data, ZhiPuResponse.class);
        if ("finish".equals(type)) {
            ZhiPuChoice choice = JSON.parseObject(completionResponse.getMeta(), ZhiPuChoice.class);
            tokens = choice.getUsage().getTotalTokens();
            if (ValidatorUtil.isNull(conversationId) && ValidatorUtil.isNotNull(completionResponse.getMeta())) {
                conversationId = choice.getTaskId();
            }
            return FINISH;
        }
        String content = completionResponse.getData();
        finishReason = FINISH;
        output.append(content).toString();
        return output.toString();
    }


    @Override
    public void onClosed(EventSource eventSource) {
        log.info("{}关闭sse连接，流式输出返回值总共{}tokens", model, tokens() - 2);
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
