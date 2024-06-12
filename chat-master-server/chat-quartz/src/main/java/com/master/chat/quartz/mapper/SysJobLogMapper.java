package com.master.chat.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.entity.SysJobLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务调度 Mapper层
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param page  分页信息
     * @param query 调度日志信息
     * @return 调度任务日志集合
     */
    IPage<SysJobLog> pageJobLog(@Param("page") IPage<SysJobLog> page, @Param("q") SysJobQry query);

    /**
     * 查询所有调度任务日志
     *
     * @param query 调度日志信息
     * @return 调度任务日志列表
     */
    List<SysJobLog> listJobLog(@Param("q") SysJobQry query);

    /**
     * 清空任务日志
     */
    void cleanJobLog();

}
