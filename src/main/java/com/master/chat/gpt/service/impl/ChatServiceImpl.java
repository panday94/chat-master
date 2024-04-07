package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.AssistantMapper;
import com.master.chat.gpt.mapper.ChatMapper;
import com.master.chat.gpt.pojo.command.ChatCommand;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.entity.Chat;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.gpt.service.IChatService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.utils.SnowFlakeUtil;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.common.validator.base.BaseAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 聊天摘要 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private AssistantMapper assistantMapper;

    /**
     * 根据id获取聊天摘要信息
     *
     * @param id 聊天摘要id
     * @return
     */
    private Chat getChat(Long id) {
        Chat chat = chatMapper.selectById(id);
        if (ValidatorUtil.isNull(chat)) {
            throw new ErrorException("聊天摘要信息不存在，无法操作");
        }
        return chat;
    }

    @Override
    public ResponseInfo<IPageInfo<ChatVO>> pageChat(Query query) {
        IPage<ChatVO> iPage = chatMapper.pageChat(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<ChatVO>> listChat(Query query) {
        return ResponseInfo.success(chatMapper.listChat(query));
    }

    @Override
    public ResponseInfo<ChatVO> getChatById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getChat(id), ChatVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo<Long> saveChat(GptCommand command) {
        String chatNumber = command.getChatNumber();
        Chat chat;
        if (ValidatorUtil.isNull(command.getChatNumber())) {
            chat = Chat.builder().chatNumber(String.valueOf(SnowFlakeUtil.snowflakeId())).assistantId(command.getAssistantId())
                    .title(command.getPrompt()).userId(command.getUserId()).build();
            chatMapper.insert(chat);
            return ResponseInfo.success(chat.getId());
        }
        chat = chatMapper.selectOne(new LambdaQueryWrapper<Chat>().eq(Chat::getChatNumber, chatNumber));
        if (ValidatorUtil.isNull(chat)) {
            chat = Chat.builder().chatNumber(chatNumber).assistantId(command.getAssistantId())
                    .title(command.getPrompt()).userId(command.getUserId()).build();
            chatMapper.insert(chat);
        }
        return ResponseInfo.success(chat.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo<ChatVO> saveChat(ChatCommand command) {
        Chat chat = DozerUtil.convertor(command, Chat.class);
        chat.setCreateUser(command.getOperater());
        chat.setChatNumber(String.valueOf(SnowFlakeUtil.snowflakeId()));
        if (ValidatorUtil.isNotNull(command.getAssistantId())) {
            Assistant assistant = assistantMapper.selectById(command.getAssistantId());
            command.setPrompt(assistant.getSystemPrompt());
        }
        BaseAssert.isBlankOrNull(command.getPrompt(), "请输入提示词");
        int maxLength = 30;
        chat.setTitle(command.getPrompt().substring(0, command.getPrompt().length() > maxLength ? maxLength : command.getPrompt().length()));
        chatMapper.insert(chat);
        return ResponseInfo.success(ChatVO.builder().chatNumber(chat.getChatNumber()).prompt(command.getPrompt()).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateChat(ChatCommand command) {
        Chat chat = getChat(command.getId());
        DozerUtil.convertor(command, chat);
        chat.setUpdateUser(command.getOperater());
        chat.setUpdateTime(LocalDateTime.now());
        chatMapper.updateById(chat);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeChatByChatNumber(String chatNumber) {
        chatMapper.delete(new LambdaQueryWrapper<Chat>().eq(Chat::getChatNumber, chatNumber));
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeChatByIds(List<Long> ids) {
        chatMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeChatById(Long id) {
        chatMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
