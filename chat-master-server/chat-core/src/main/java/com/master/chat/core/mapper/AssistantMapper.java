package com.master.chat.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.client.model.dto.Query;
import com.master.chat.core.pojo.entity.Assistant;
import com.master.chat.core.pojo.vo.AssistantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI助理功能 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public interface AssistantMapper extends BaseMapper<Assistant> {

    /**
     * 分页查询AI助理功能列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<AssistantVO> pageAssistant(IPage page, @Param("q") Query query);

    /**
     * 查询AI助理功能列表
     *
     * @param query 查询条件
     * @return
     */
    List<AssistantVO> listAssistant(@Param("q") Query query);

    /**
     * 查询AI助理功能列表
     *
     * @param query 查询条件
     * @return
     */
    List<AssistantVO> listAssistantRandom(@Param("q") Query query);

    /**
     * 查询AI助理功能
     *
     * @param query 查询条件
     * @return
     */
    AssistantVO getAssistant(@Param("q") Query query);

}
