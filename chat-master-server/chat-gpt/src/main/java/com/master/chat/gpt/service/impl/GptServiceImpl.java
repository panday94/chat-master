package com.master.chat.gpt.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatRoleEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatCommand;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.model.dto.ModelDTO;
import com.master.chat.client.model.dto.Query;
import com.master.chat.client.service.BaseConfigService;
import com.master.chat.client.service.GptService;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.config.dto.AppInfoDTO;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ProhibitVisitException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.framework.validator.base.BaseAssert;
import com.master.chat.gpt.constant.BaseConfigConstant;
import com.master.chat.gpt.mapper.AssistantMapper;
import com.master.chat.gpt.mapper.ModelMapper;
import com.master.chat.gpt.mapper.UserMapper;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.entity.User;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.gpt.service.IChatService;
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
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@Service
public class GptServiceImpl implements GptService {
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
    private BaseConfigService baseConfigService;

    @Override
    public ModelDTO getModel(String model) {
        Query query = new Query();
        query.put("model", model);
        return DozerUtil.convertor(modelMapper.getModel(query), ModelDTO.class);
    }

    @Override
    public Long saveChat(ChatCommand command) {
        return chatService.saveChat(command).getData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String chatMessage(ChatCommand command) {
        return chatMessageService.saveChatMessage(command).getData();
    }

    @Override
    public ChatMessageDTO getMessageByConverstationId(String conversationId) {
        ChatMessageDTO chatMessage = chatMessageService.getChatByMessageId(conversationId).getData();
        return chatMessage;
    }

    @Override
    public List<ChatMessageDTO> listMessageByConverstationId(String uid, String conversationId) {
        User user = userMapper.getUserByUid(uid);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveChatMessage(ChatMessageCommand command) {
        ResponseInfo response = chatMessageService.saveChatMessage(command);
        if (!response.isSuccess()) {
            return;
        }
        if (!ChatRoleEnum.ASSISTANT.getValue().equals(command.getRole())) {
            return;
        }
        if (!ChatStatusEnum.SUCCESS.getValue().equals(command.getStatus())) {
            return;
        }
        // 扣除用户电量
        User user = userMapper.getUserByChatId(command.getChatId());
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.lambda().set(User::getNum, user.getNum() - 1).eq(BaseEntity::getId, user.getId());
        userMapper.update(null, uw);
    }

    @Override
    public List<ChatMessageDTO> saveChatMessage(ChatCommand command, Long chatId, String messageId) {
        return chatMessageService.saveChatMessage(command, chatId, messageId).getData();
    }

    /**
     * 校验对话数据
     */
    @Override
    public ChatCommand validateGptCommand(ChatCommand command) {
        BaseAssert.isBlankOrNull(command.getModel(), "缺少模型信息");
        BaseAssert.isBlankOrNull(command.getPrompt(), "缺少prompt");
        ModelDTO model = getModel(command.getModel());
        if (ValidatorUtil.isNull(model)) {
            throw new BusinessException("模型已经不存在啦，请切换模型进行回复～");
        }
        if (StatusEnum.DISABLED.getValue().equals(model.getStatus())) {
            throw new BusinessException("该模型已被禁用，请切换模型进行回复～");
        }
        ChatModelEnum modelEnum = ChatModelEnum.getEnum(command.getModel());
        if (ValidatorUtil.isNull(modelEnum)) {
            throw new BusinessException("未知的模型类型，功能未接入");
        }
        // 兼容老版本，没有选择版本时使用模型默认版本
        if (ValidatorUtil.isNull(command.getModelVersion())) {
            ModelDTO modelDTO = getModel(command.getModel());
            command.setModelVersion(modelDTO.getVersion());
        }
        String messageId = UUID.fastUUID().toString();
        command.setConversationId(messageId);
        //校验用户
        if (ValidatorUtil.isNull(command.getApi()) || !command.getApi()) {
            validateUser(command);
        }
        return command;
    }

    /**
     * 校验账户
     *
     * @param command
     */
    private void validateUser(ChatCommand command) {
        User user = userMapper.selectById(command.getUserId());
        if (ValidatorUtil.isNull(user)) {
            throw new ProhibitVisitException();
        }
        // 是否限制访问ChatMASTER
        AppInfoDTO appInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.APP_INFO, AppInfoDTO.class);
        if (ValidatorUtil.isNotNull(appInfo) && ValidatorUtil.isNotNull(appInfo.getIsGPTLimit()) && appInfo.getIsGPTLimit().equals(StatusEnum.ENABLED.getValue())) {
            return;
        }
        if (user.getNum() < 1) {
            throw new BusinessException("电量不足，开通会员享受更多权益");
        }
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
