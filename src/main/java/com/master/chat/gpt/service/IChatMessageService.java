package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.entity.ChatMessage;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 对话消息 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IChatMessageService extends IService<ChatMessage> {

    /**
     * 查询对话消息分页列表
     *
     * @param query 查询条件
     * @return 对话消息集合
     */
    ResponseInfo<IPageInfo<ChatMessageVO>> pageChatMessage(Query query);

    /**
     * 查询对话消息列表
     *
     * @param query 查询条件
     * @return 对话消息集合
     */
    ResponseInfo<List<ChatMessageVO>> listChatMessage(Query query);

    /**
     * 查询对话消息列表
     *
     * @param query 查询条件
     * @return 对话消息集合
     */
    ResponseInfo<List<ChatMessageDTO>> listChatMessage(Long chatId);

    /**
     * 根据主键查询对话消息
     *
     * @param id 对话消息主键
     * @return 对话消息
     */
    ResponseInfo<ChatMessageVO> getChatMessageById(Long id);

    /**
     * 根据消息id查询对话id
     *
     * @param id 对话消息主键
     * @return 对话消息
     */
    ResponseInfo<Long> getChatIdByMessageId(String messageId);

    /**
     * 根据消息id查询对话id
     *
     * @param id 对话消息主键
     * @return 对话消息
     */
    ResponseInfo<ChatMessageDTO> getChatByMessageId(String messageId);

    /**
     * 新增对话消息（网页对话 流式输出使用）
     *
     * @param command 对话消息
     * @return 结果
     */
    ResponseInfo<String> saveChatMessage(GptCommand command);

    /**
     * 新增对话消息（json使用）
     *
     * @param command 对话消息
     * @return 结果
     */
    ResponseInfo<String> saveChatMessage(ChatMessageCommand command);

    /**
     * 新增对话消息（json使用）
     *
     * @param command 对话消息
     * @return 结果
     */
    ResponseInfo<List<ChatMessageDTO>> saveChatMessage(GptCommand command, Long chatId);

    /**
     * 修改对话状态
     *
     * @param messageId 消息id
     * @param status    状态
     * @return 结果
     */
    ResponseInfo updateMessageStatus(String messageId, Integer status);

    /**
     * 更新对话使用token数
     *
     * @param messageId  消息id
     * @param usedTokens 使用token数
     * @return
     */
    ResponseInfo updateMessageUsedTokens(String messageId, Long usedTokens);

    /**
     * 批量删除对话消息
     *
     * @param ids 需要删除的对话消息主键集合
     * @return 结果
     */
    ResponseInfo removeChatMessageByIds(List<Long> ids);

    /**
     * 删除对话消息信息
     *
     * @param id 对话消息主键
     * @return 结果
     */
    ResponseInfo removeChatMessageByMessageId(String messageId);

}
