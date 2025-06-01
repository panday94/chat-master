package com.master.chat.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.client.model.dto.Query;
import com.master.chat.core.pojo.entity.Chat;
import com.master.chat.core.pojo.vo.ChatVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天摘要 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public interface ChatMapper extends BaseMapper<Chat> {

    /**
     * 分页查询聊天摘要列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<ChatVO> pageChat(IPage page, @Param("q") Query query);

    /**
     * 查询聊天摘要列表
     *
     * @param query 查询条件
     * @return
     */
    List<ChatVO> listChat(@Param("q") Query query);

    /**
     * 查询聊天摘要
     *
     * @param query 查询条件
     * @return
     */
    ChatVO getChat(@Param("q") Query query);

}
