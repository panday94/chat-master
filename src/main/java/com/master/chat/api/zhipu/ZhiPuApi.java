package com.master.chat.api.zhipu;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.master.chat.api.base.entity.ChatData;
import com.master.chat.api.base.enums.ChatContentEnum;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.service.IChatMessageService;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 智谱清言V4接口
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class ZhiPuApi {
    private HttpServletResponse response;
    private StringBuffer output = new StringBuffer();
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason = "stop";
    private String version;
    private Boolean error;
    private String errTxt;

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.addMixIn(ChatFunction.class, ChatFunctionMixIn.class);
        mapper.addMixIn(ChatCompletionRequest.class, ChatCompletionRequestMixIn.class);
        mapper.addMixIn(ChatFunctionCall.class, ChatFunctionCallMixIn.class);
        return mapper;
    }

    /**
     * 流式响应
     *
     * @param request
     * @param query
     */
    public ZhiPuApi(HttpServletResponse response, Long chatId, String parentMessageId, String version) {
        this.response = response;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.version = version;
        if (response == null) {
            log.error("客户端非sse推送");
            return;
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        log.info("智谱清言建立sse连接...");
    }

    /**
     * 流式响应
     *
     * @param request
     */
    public Boolean streamChat(ModelApiResponse sseModelApiResp) {
        if (sseModelApiResp.isSuccess()) {
            AtomicBoolean isFirst = new AtomicBoolean(true);
            ChatMessageAccumulator chatMessageAccumulator = mapStreamToAccumulator(sseModelApiResp.getFlowable())
                    .doOnNext(accumulator -> {
                        {
//                            if (isFirst.getAndSet(false)) {
//                                System.out.print("Response: ");
//                            }
//                            if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
//                                String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
//                                System.out.println("tool_calls: " + jsonString);
//                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                                log.info("智谱清言返回，数据：{}", accumulator.getDelta().getContent());
                                output.append(accumulator.getDelta().getContent()).toString();
                                // 向客户端发送信息
                                output();
                            }
                        }
                    })
                    .doOnComplete(System.out::println)
                    .lastElement()
                    .blockingGet();

            Choice choice = new Choice(chatMessageAccumulator.getChoice().getFinishReason(), 0L, chatMessageAccumulator.getDelta());
            List<Choice> choices = new ArrayList<>();
            choices.add(choice);
            ModelData data = new ModelData();
            data.setChoices(choices);
            data.setUsage(chatMessageAccumulator.getUsage());
            data.setId(chatMessageAccumulator.getId());
            data.setCreated(chatMessageAccumulator.getCreated());
            sseModelApiResp.setFlowable(null);
            sseModelApiResp.setData(data);
            this.conversationId = data.getId();
            log.info("智谱清言返回数据结束了:{}", JSON.toJSONString(sseModelApiResp));
            ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                    .model(ChatModelEnum.ZHIPU.getValue()).modelVersion(version)
                    .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                    .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(Long.valueOf(data.getUsage().getTotalTokens()))
                    .build();
            com.master.chat.common.utils.ApplicationContextUtil.getBean(IChatMessageService.class).saveChatMessage(chatMessage);
            return false;
        } else {
            log.error("智谱清言连接异常data：{}", JSON.toJSONString(sseModelApiResp));
            return true;
        }
    }

    private void output() {
        try {
            String text = output.toString();
            ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                    .parentMessageId(parentMessageId)
                    .role(ChatRoleEnum.ASSISTANT.getValue()).content(text).build();
            response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("消息错误", e);
            throw new ErrorException();
        }
    }

    public static Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null, chunk.getChoices().get(0), chunk.getUsage(), chunk.getCreated(), chunk.getId());
        });
    }

}
