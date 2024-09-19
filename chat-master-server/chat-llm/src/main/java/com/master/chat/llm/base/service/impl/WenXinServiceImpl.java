package com.master.chat.llm.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatContentEnum;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.service.GptService;
import com.master.chat.llm.base.entity.ChatData;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.wenxin.WenXinClient;
import com.master.chat.llm.wenxin.entity.request.ChatCompletionMessage;
import com.master.chat.llm.wenxin.entity.request.ImagesBody;
import com.master.chat.llm.wenxin.entity.response.ChatResponse;
import com.master.chat.llm.wenxin.entity.response.ImageResponse;
import com.master.chat.llm.wenxin.enums.ModelEnum;
import com.master.chat.llm.wenxin.listener.SSEListener;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 文心一言 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class WenXinServiceImpl implements ModelService {
    private static WenXinClient wenXinClient;
    private static GptService gptService;

    @Autowired
    public WenXinServiceImpl(GptService gptService, WenXinClient wenXinClient) {
        WenXinServiceImpl.gptService = gptService;
        WenXinServiceImpl.wenXinClient = wenXinClient;
    }

    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        if (ValidatorUtil.isNull(wenXinClient)) {
            throw new BusinessException("文心一言无有效token，请切换其他模型进行聊天");
        }
        List<ChatCompletionMessage> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            messages.add(ChatCompletionMessage.builder().role(v.getRole()).content(v.getContent()).build());
        });
        ModelEnum modelEnum = ModelEnum.ERNIE_Bot_turbo;
        if (ValidatorUtil.isNotNull(version)) {
            modelEnum = ModelEnum.getEnum(version);
        }
        if (ValidatorUtil.isNull(modelEnum)) {
            throw new BusinessException("未知的模型");
        }
        ResponseInfo<ChatResponse> wenxinResponse = wenXinClient.chat(messages, modelEnum);
        if (!wenxinResponse.isSuccess()) {
            throw new LLMException(wenxinResponse.getMsg());
        }
        ChatResponse response = wenxinResponse.getData();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getId())
                .model(ChatModelEnum.WENXIN.getValue()).modelVersion(modelEnum.getLabel())
                .content(response.getResult()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(wenXinClient.getApiKey()).usedTokens(response.getUsage().getTotalTokens())
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isWs, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNull(wenXinClient.getApiKey())) {
            throw new BusinessException("未加载到密钥信息");
        }
        if (isDraw) {
            return image(sseEmitter, response, chatId, conversationId, prompt);
        }
        List<ChatCompletionMessage> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            messages.add(ChatCompletionMessage.builder().role(v.getRole()).content(v.getContent()).build());
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.WENXIN.getValue(), version, uid, isWs);
        ModelEnum model = ValidatorUtil.isNotNull(version) ? ModelEnum.getEnum(version) : ModelEnum.ERNIE_Bot_turbo;
        if (ValidatorUtil.isNull(model)) {
            throw new BusinessException("文心大模型不存在，请检查模型名称。");
        }
        wenXinClient.streamChat(messages, sseListener, model);
        if (isWs) {
            return false;
        }
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

    /**
     * 文生图
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean image(SseEmitter sseEmitter, HttpServletResponse response, Long chatId, String conversationId, String prompt) {
        // 通知客户端返回为图片
        ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                .parentMessageId(conversationId)
                .role(ChatRoleEnum.ASSISTANT.getValue()).contentType(ChatContentEnum.IMAGE.getValue()).build();
        response.getWriter().write(JSON.toJSONString(chatData));
        response.getWriter().flush();

        // 请求生成图片 并返回base64信息
        ImagesBody body = ImagesBody.builder().prompt(prompt).build();
        ResponseInfo<ImageResponse> imageResponse = wenXinClient.image(body);
        if (!imageResponse.isSuccess()) {
            throw new BusinessException(imageResponse.getMsg());
        }
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        ImageResponse image = imageResponse.getData();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(image.getId()).parentMessageId(conversationId)
                .model(ChatModelEnum.WENXIN.getValue()).modelVersion(ModelEnum.STABLE_DIFFUSION_XL.getLabel())
                .content(JSON.toJSONString(image.getData())).contentType(ChatContentEnum.IMAGE.getValue()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).usedTokens(image.getUsage().getTotalTokens())
                .build();
        gptService.saveChatMessage(chatMessage);
        chatData.setContent(image.getData());
        response.getWriter().write("\n" + JSON.toJSONString(chatData));
        response.getWriter().flush();
        return false;
    }

}
