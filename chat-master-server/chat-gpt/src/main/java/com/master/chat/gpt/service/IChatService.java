package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.client.model.command.ChatCommand;
import com.master.chat.gpt.pojo.entity.Chat;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 聊天摘要 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
     * 新增聊天摘要(同步)
     *
     * @param command 聊天摘要
     * @return 结果
     */
    ResponseInfo<Long> saveChat(ChatCommand command);

    /**
     * 新增聊天摘要(SSE)
     *
     * @param command 聊天摘要
     * @return 结果
     */
    ResponseInfo<ChatVO> saveSSEChat(ChatCommand command);

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
