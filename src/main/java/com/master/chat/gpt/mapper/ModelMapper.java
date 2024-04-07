package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.Model;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 大模型信息 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
