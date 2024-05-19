package com.master.chat.gpt.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.base.exception.ChatMasterException;
import com.master.chat.api.xfyun.SparkClient;
import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.SparkSyncChatResponse;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.entity.response.SparkTextUsage;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.IntegerEnum;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.common.validator.base.BaseAssert;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.mapper.AssistantMapper;
import com.master.chat.gpt.mapper.ModelMapper;
import com.master.chat.gpt.mapper.OpenkeyMapper;
import com.master.chat.gpt.mapper.UserMapper;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.entity.User;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.gpt.service.IChatService;
import com.master.chat.gpt.service.IGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * chatgpt接口实现类
 *
 * @author: Yang
 * @date: 2023/5/5
 * @version: 1.0.0
 */
@Slf4j
@Service
public class GptServiceImpl implements IGptService {
    private static SparkClient sparkClient;
    @Autowired
    private IChatService chatService;
    @Autowired
    private IChatMessageService chatMessageService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AssistantMapper assistantMapper;
    @Autowired
    private OpenkeyMapper openkeyMapper;

    @Autowired
    public void setGptClient(SparkClient sparkClient) {
        GptServiceImpl.sparkClient = sparkClient;
    }

    @Override
    public ResponseInfo<ModelVO> getModel(String model) {
        Query query = new Query();
        query.put("model", model);
        return ResponseInfo.success(modelMapper.getModel(query));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo<ChatMessageVO> chat(GptCommand command) {
        BaseAssert.isBlankOrNull(command.getModel(), "缺少模型信息");
        BaseAssert.isBlankOrNull(command.getPrompt(), "缺少prompt");
        ChatModelEnum modelEnum = ChatModelEnum.getEnum(command.getModel());
        if (ValidatorUtil.isNull(modelEnum)) {
            throw new BusinessException("未知的模型类型，功能未接入");
        }
        //校验用户
        if (!command.getApi()) {
            validateUser(command.getUserId());
        }
        ChatMessageDTO userMessage = new ChatMessageDTO();
        ChatMessageCommand chatMessage;
        try {
            Long chatId = chatService.saveChat(command).getData();
            List<ChatMessageDTO> messages = chatMessageService.saveChatMessage(command, chatId).getData();
            userMessage = messages.get(messages.size() - 1);
            if (ValidatorUtil.isNullIncludeArray(messages)) {
                throw new BusinessException("消息发送失败");
            }
            switch (modelEnum) {
                case SPARK:
                    chatMessage = chatBySpark(chatId, command.getModelVersion(), messages);
                    break;
                default:
                    throw new BusinessException("未知的模型类型，功能未接入");
            }
        } catch (ChatMasterException e) {
            chatMessageService.updateMessageStatus(userMessage.getMessageId(), IntegerEnum.THREE.getValue());
            return ResponseInfo.success();
        }
        chatMessage.setParentMessageId(userMessage.getMessageId());
        chatMessageService.saveChatMessage(chatMessage);
        chatMessageService.updateMessageStatus(userMessage.getMessageId(), IntegerEnum.TWO.getValue());
        updateToken(chatMessage.getAppKey(), chatMessage.getUsedTokens(), chatMessage.getMessageId());
        return ResponseInfo.success(DozerUtil.convertor(chatMessage, ChatMessageVO.class));
    }

    /**
     * 讯飞星火模型聊天
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    private ChatMessageCommand chatBySpark(Long chatId, String version, List<ChatMessageDTO> chatMessages) {
        if (ValidatorUtil.isNull(sparkClient)) {
            throw new BusinessException("讯飞星火无有效token，请切换其他模型进行聊天");
        }
        List<SparkMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            SparkMessage currentMessage = new SparkMessage(v.getRole(), v.getContent());
            messages.add(currentMessage);
        });
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.5)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(ValidatorUtil.isNotNull(version) ? SparkApiVersion.getEnum(version) : SparkApiVersion.V2_0)
                .chatId(chatId.toString())
                .build();
        SparkSyncChatResponse response = sparkClient.chat(sparkRequest);
        if (!response.isSuccess()) {
            throw new ChatMasterException(response.getErrTxt());
        }
        SparkTextUsage textUsage = response.getTextUsage();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(UUID.fastUUID().toString())
                .model(ChatModelEnum.SPARK.getValue()).modelVersion(SparkApiVersion.V2_0.getVersion())
                .content(response.getContent()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(sparkClient.apiKey).usedTokens(Long.valueOf(textUsage.getTotalTokens()))
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }


    /**
     * 更新token额度
     */
    private void updateToken(String appKey, Long usedTokens, String messageId) {
        openkeyMapper.updateUsedTokens(appKey, usedTokens);
        chatMessageService.updateMessageUsedTokens(messageId, usedTokens);
    }

    @Override
    public ChatMessageDTO getMessageByConverstationId(String conversationId) {
        ChatMessageDTO chatMessage = chatMessageService.getChatByMessageId(conversationId).getData();
        return chatMessage;
    }

    @Override
    public List<ChatMessageDTO> listMessageByConverstationId(Long userId, String conversationId) {
        User user = userMapper.selectById(userId);
        Boolean context = user.getContext();
        ChatMessageDTO chatMessage = chatMessageService.getChatByMessageId(conversationId).getData();
        List<ChatMessageDTO> chatMessages = new ArrayList<>();
        if (ValidatorUtil.isNotNull(context) && context) {
            chatMessages = chatMessageService.listChatMessage(chatMessage.getChatId()).getData();
        }
        chatMessages.add(chatMessage);
        ChatVO chat = chatService.getChatById(chatMessage.getChatId()).getData();
        if (ValidatorUtil.isNotNull(chat) && ValidatorUtil.isNotNullAndZero(chat.getAssistantId())) {
            Assistant assistant = assistantMapper.selectById(chat.getAssistantId());
            if (ValidatorUtil.isNotNull(assistant.getSystemPrompt())) {
                chatMessages.add(0, ChatMessageDTO.builder().role(ChatRoleEnum.SYSTEM.getValue()).content(assistant.getSystemPrompt()).build());
            }
        }
        return chatMessages;
    }

    /**
     * 校验账户
     *
     * @param command
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validateUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user.getNum() <= 0) {
            throw new ValidateException("剩余次数不足，请开通会员");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restoreNum(Long userId) {
        // 是否限制访问ChatMASTER
    }

    @Override
    public void updateMessageStatus(String messageId, Integer status) {
        chatMessageService.updateMessageStatus(messageId, status);
    }

    @Override
    public void updateMessageUsedTokens(String messageId, Long usedTokens) {
        chatMessageService.updateMessageUsedTokens(messageId, usedTokens);
    }

}
