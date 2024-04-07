package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.sys.pojo.entity.SysConfig;
import com.master.chat.common.api.QueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 查询参数配置列表
     *
     * @param iPage 分页信息
     * @param query 查询条件
     * @return 参数配置集合
     */
    IPage<SysConfig> pageConfig(IPage<SysConfig> iPage, @Param("q") QueryDTO query);

    /**
     * 查询参数配置列表
     *
     * @param query 查询条件
     * @return 参数配置集合
     */
    List<SysConfig> listConfig(@Param("q") QueryDTO query);

}
