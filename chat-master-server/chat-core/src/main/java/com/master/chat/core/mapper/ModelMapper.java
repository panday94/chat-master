package com.master.chat.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.client.model.dto.Query;
import com.master.chat.core.pojo.entity.Model;
import com.master.chat.core.pojo.vo.ModelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 大模型信息 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public interface ModelMapper extends BaseMapper<Model> {

    /**
     * 分页查询大模型信息列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<ModelVO> pageModel(IPage page, @Param("q") Query query);

    /**
     * 查询大模型信息列表
     *
     * @param query 查询条件
     * @return
     */
    List<ModelVO> listModel(@Param("q") Query query);

    /**
     * 查询大模型信息
     *
     * @param query 查询条件
     * @return
     */
    ModelVO getModel(@Param("q") Query query);

}
