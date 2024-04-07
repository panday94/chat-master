package com.master.chat.gpt.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.api.base.enums.ChatContentEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.mapper.AssistantMapper;
import com.master.chat.gpt.mapper.ChatMapper;
import com.master.chat.gpt.mapper.ChatMessageMapper;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.entity.Chat;
import com.master.chat.gpt.pojo.entity.ChatMessage;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.IntegerEnum;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对话消息 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements IChatMessageService {
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private AssistantMapper assistantMapper;

    /**
     * 根据id获取对话消息信息
     *
     * @param id 对话消息id
     * @return
     */
    private ChatMessage getChatMessage(Long id) {
        ChatMessage chatMessage = chatMessageMapper.selectById(id);
        if (ValidatorUtil.isNull(chatMessage)) {
            throw new ErrorException("对话消息信息不存在，无法操作");
        }
        return chatMessage;
    }

    /**
     * 根据id获取对话消息信息
     *
     * @param id 对话消息id
     * @return
     */
    private ChatMessage getChatMessageByMessageId(String messageId) {
        ChatMessage chatMessage = chatMessageMapper.selectOne(new LambdaQueryWrapper<ChatMessage>().eq(ChatMessage::getMessageId, messageId));
        if (ValidatorUtil.isNull(chatMessage)) {
            throw new ErrorException("对话消息信息不存在，无法操作");
        }
        return chatMessage;
    }

    @Override
    public ResponseInfo<IPageInfo<ChatMessageVO>> pageChatMessage(Query query) {
        IPage<ChatMessageVO> iPage = chatMessageMapper.pageChatMessage(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<ChatMessageVO>> listChatMessage(Query query) {
        return ResponseInfo.success(chatMessageMapper.listChatMessage(query));
    }

    @Override
    public ResponseInfo<List<ChatMessageDTO>> listChatMessage(Long chatId) {
        List<ChatMessage> chatMessages = chatMessageMapper.selectList(new LambdaQueryWrapper<ChatMessage>()
                .eq(ChatMessage::getChatId, chatId).eq(ChatMessage::getStatus, IntegerEnum.TWO.getValue())
                .orderByDesc(ChatMessage::getId).last("limit 10"));
        chatMessages = chatMessages.stream().sorted(Comparator.comparing(ChatMessage::getId)).collect(Collectors.toList());
        return ResponseInfo.success(DozerUtil.convertor(chatMessages, ChatMessageDTO.class));
    }

    @Override
    public ResponseInfo<ChatMessageVO> getChatMessageById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getChatMessage(id), ChatMessageVO.class));
    }

    @Override
    public ResponseInfo<Long> getChatIdByMessageId(String messageId) {
        ChatMessage chatMessage = getChatMessageByMessageId(messageId);
        return ResponseInfo.success(chatMessage.getChatId());
    }

    @Override
    public ResponseInfo<ChatMessageDTO> getChatByMessageId(String messageId) {
        ChatMessage chatMessage = getChatMessageByMessageId(messageId);
        return ResponseInfo.success(DozerUtil.convertor(chatMessage, ChatMessageDTO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo<String> saveChatMessage(GptCommand command) {
        Chat chat = chatMapper.selectOne(new LambdaQueryWrapper<Chat>().eq(Chat::getChatNumber, command.getChatNumber()));
        if (ValidatorUtil.isNull(chat)) {
            return ResponseInfo.validateFail("对话不存在，请刷新页面");
        }
        ChatMessage chatMessage = ChatMessage.builder()
                .chatId(chat.getId()).messageId(UUID.fastUUID().toString()).model(command.getModel())
                .content(command.getPrompt()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.USER.getValue()).status(ChatStatusEnum.REPLY.getValue())
                .build();
        chatMessage.setCreateUser(command.getOperater());
        chatMessageMapper.insert(chatMessage);
        return ResponseInfo.success(chatMessage.getMessageId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo<String> saveChatMessage(ChatMessageCommand command) {
        ChatMessage chatMessage = DozerUtil.convertor(command, ChatMessage.class);
        chatMessageMapper.insert(chatMessage);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo<List<ChatMessageDTO>> saveChatMessage(GptCommand command, Long chatId) {
        List<ChatMessageDTO> messages = listChatMessage(chatId).getData();
        ChatMessage chatMessage = ChatMessage.builder()
                .createUser(command.getOperater()).chatId(chatId).messageId(UUID.fastUUID().toString())
                .model(command.getModel())
                .content(command.getPrompt()).role(ChatRoleEnum.USER.getValue())
                .status(ChatStatusEnum.REPLY.getValue())
                .build();
        chatMessageMapper.insert(chatMessage);
        messages.add(DozerUtil.convertor(chatMessage, ChatMessageDTO.class));
        if (ValidatorUtil.isNotNullAndZero(command.getAssistantId())) {
            Assistant assistant = assistantMapper.selectById(command.getAssistantId());
            if (ValidatorUtil.isNotNull(assistant.getSystemPrompt())) {
                messages.add(0, ChatMessageDTO.builder().role(ChatRoleEnum.SYSTEM.getValue()).content(assistant.getSystemPrompt()).build());
            }
        } else if (ValidatorUtil.isNotNull(command.getSystemPrompt())) {
            messages.add(0, ChatMessageDTO.builder().role(ChatRoleEnum.SYSTEM.getValue()).content(command.getSystemPrompt()).build());
        }
        return ResponseInfo.success(messages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateMessageStatus(String messageId, Integer status) {
        chatMessageMapper.updateMessageStatus(messageId, status);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateMessageUsedTokens(String messageId, Long usedTokens) {
        chatMessageMapper.updateMessageUsedTokens(messageId, usedTokens);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeChatMessageByIds(List<Long> ids) {
        chatMessageMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeChatMessageByMessageId(String messageId) {
        ChatMessage chatMessage = getChatMessageByMessageId(messageId);
        chatMessageMapper.delete(new LambdaQueryWrapper<ChatMessage>().eq(ChatMessage::getMessageId, messageId));
        chatMessageMapper.delete(new LambdaQueryWrapper<ChatMessage>().eq(ChatMessage::getMessageId, chatMessage.getParentMessageId()));
        return ResponseInfo.success();
    }

}
