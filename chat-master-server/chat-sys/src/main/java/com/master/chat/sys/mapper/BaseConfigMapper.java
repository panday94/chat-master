package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.sys.pojo.entity.BaseConfig;
import com.master.chat.sys.pojo.vo.BaseConfigVO;
import com.master.chat.client.model.dto.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础配置 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface BaseConfigMapper extends BaseMapper<BaseConfig> {

    /**
    * 分页查询基础配置列表
    *
    * @param page  分页参数
    * @param query 查询条件
    * @return
    */
    IPage<BaseConfigVO> pageBaseConfig(IPage page, @Param("q") Query query);

    /**
     * 查询基础配置列表
     *
     * @param query 查询条件
     * @return
     */
    List<BaseConfigVO> listBaseConfig(@Param("q") Query query);

    /**
     * 查询基础配置
     *
     * @param query 查询条件
     * @return
     */
    BaseConfigVO getBaseConfig(@Param("q") Query query);

}
