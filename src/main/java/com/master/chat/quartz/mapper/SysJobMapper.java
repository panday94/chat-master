package com.master.chat.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.entity.SysJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务调度日志 Mapper层
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface SysJobMapper extends BaseMapper<SysJob> {

    /**
     * 查询调度任务日志集合
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 调度任务列表
     */
    IPage<SysJob> pageJob(IPage<SysJob> page, @Param("q") SysJobQry query);

    /**
     * 查询所有调度任务
     *
     * @param query 查询条件
     * @return 调度任务列表
     */
    List<SysJob> listJob(@Param("q") SysJobQry query);

}
