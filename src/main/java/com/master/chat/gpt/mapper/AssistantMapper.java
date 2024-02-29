package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.vo.AssistantVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI助理功能 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
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
