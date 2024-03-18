package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.gpt.pojo.command.ChatCommand;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.entity.Chat;
import com.master.chat.gpt.pojo.vo.ChatVO;

import java.util.List;

/**
 * 聊天摘要 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IChatService extends IService<Chat> {

    /**
     * 查询聊天摘要分页列表
     *
     * @param query 查询条件
     * @return 聊天摘要集合
     */
    ResponseInfo<IPageInfo<ChatVO>> pageChat(Query query);

    /**
     * 查询聊天摘要列表
     *
     * @param query 查询条件
     * @return 聊天摘要集合
     */
    ResponseInfo<List<ChatVO>> listChat(Query query);

    /**
     * 根据主键查询聊天摘要
     *
     * @param id 聊天摘要主键
     * @return 聊天摘要
     */
    ResponseInfo<ChatVO> getChatById(Long id);

    /**
     * 新增聊天摘要
     *
     * @param command 聊天摘要
     * @return 结果
     */
    ResponseInfo<Long> saveChat(GptCommand command);

    /**
     * 新增聊天摘要
     *
     * @param command 聊天摘要
     * @return 结果
     */
    ResponseInfo<ChatVO> saveChat(ChatCommand command);

    /**
     * 修改聊天摘要
     *
     * @param command 聊天摘要
     * @return 结果
     */
    ResponseInfo updateChat(ChatCommand command);

    /**
     * 批量删除聊天摘要
     *
     * @param ids 需要删除的聊天摘要主键集合
     * @return 结果
     */
    ResponseInfo removeChatByIds(List<Long> ids);

    /**
     * 删除聊天摘要信息
     *
     * @param id 聊天摘要主键
     * @return 结果
     */
    ResponseInfo removeChatByChatNumber(String chatNumber);

    /**
     * 删除聊天摘要信息
     *
     * @param id 聊天摘要主键
     * @return 结果
     */
    ResponseInfo removeChatById(Long id);

}
