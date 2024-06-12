package com.master.chat.llm.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.service.ModelService;
import com.master.chat.llm.chatglm.ChatGLMClient;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.framework.validator.ValidatorUtil;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 智谱清言 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class ChatGLMServiceImpl implements ModelService {
    private static ChatGLMClient chatGLMClient;

    @Autowired
    public ChatGLMServiceImpl(ChatGLMClient chatGLMClient) {
        ChatGLMServiceImpl.chatGLMClient = chatGLMClient;
    }

    /**
     * 智谱清言模型聊天
     * 支持超拟人大模型，将modelId替换为characterglm
     * query中增加参数{meta：{"user_info": "我是陆星辰","bot_info:"苏梦远，本名苏远心，是一位当红的国内女歌手及演员。", "user_name": "陆星辰", "bot_name": "苏梦远"}}
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    @Override
    public ChatMessageCommand chat(List<ChatMessageDTO> chatMessages, Boolean isDraw, Long chatId, String version) {
        if (ValidatorUtil.isNull(chatGLMClient)) {
            throw new BusinessException("智谱清言无有效token，请切换其他模型进行聊天");
        }
        List<ChatMessage> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            messages.add(new ChatMessage(v.getRole(), v.getContent()));
        });
        String modelVaersion = ValidatorUtil.isNotNull(version) ? version : Constants.ModelChatGLM3TURBO;
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(modelVaersion)
                .messages(messages)
                .build();
        // 关闭搜索示例
        //  modelApiRequest.setRef(new HashMap<String, Object>(){{
        //    put("enable",false);
        // }});
        // 开启搜索示例
        // modelApiRequest.setRef(new HashMap<String, Object>(){{
        //    put("enable",true);
        //    put("search_query","历史");
        //  }});
        ModelApiResponse response = chatGLMClient.chat(request);
        if (!response.isSuccess()) {
            throw new LLMException(response.getMsg());
        }
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getData().getTaskId())
                .model(ChatModelEnum.CHATGLM.getValue()).modelVersion(modelVaersion)
                .content(response.getData().getChoices().get(0).getDelta().getContent()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(chatGLMClient.getAppKey()).usedTokens(Long.valueOf(response.getData().getUsage().getTotalTokens()))
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    @Override
    @SneakyThrows
    public Boolean streamChat(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages, Boolean isDraw,
                              Long chatId, String conversationId, String prompt, String version, String uid) {
        if (ValidatorUtil.isNull(chatGLMClient.getAppKey())) {
            throw new BusinessException("未加载到密钥信息");
        }
        List<ChatMessage> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            messages.add(new ChatMessage(v.getRole(), v.getContent()));
        });
        String modelVaersion = ValidatorUtil.isNotNull(version) ? version : Constants.ModelChatGLM3TURBO;
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(modelVaersion)
                .stream(Boolean.TRUE)
                .messages(messages)
                .build();
        Boolean flag = chatGLMClient.streamChat(response, chatCompletionRequest, chatId, conversationId, modelVaersion, uid);
        return flag;
    }

}
