package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.ChatMessage;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 对话消息 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    /**
     * 分页查询对话消息列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<ChatMessageVO> pageChatMessage(IPage page, @Param("q") Query query);

    /**
     * 查询对话消息列表
     *
     * @param query 查询条件
     * @return
     */
    List<ChatMessageVO> listChatMessage(@Param("q") Query query);

    /**
     * 查询对话消息
     *
     * @param query 查询条件
     * @return
     */
    ChatMessageVO getChatMessage(@Param("q") Query query);

    /**
     * 更新消息状态
     *
     * @param messageId
     * @param status
     */
    @Update("update gpt_chat_message t set t.status = #{status} where t.message_id = #{messageId}")
    void updateMessageStatus(@Param("messageId") String messageId, @Param("status") Integer status);

    /**
     * 更新消息使用token
     *
     * @param messageId  消息id
     * @param usedTokens 使用token
     */
    @Update("update gpt_chat_message t set t.used_tokens = #{usedTokens} where t.message_id = #{messageId}")
    void updateMessageUsedTokens(@Param("messageId") String messageId, @Param("usedTokens") Long usedTokens);

}
