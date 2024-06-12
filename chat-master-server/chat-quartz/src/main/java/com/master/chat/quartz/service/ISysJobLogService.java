package com.master.chat.quartz.service;

import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.entity.SysJobLog;
import com.master.chat.quartz.pojo.vo.SysJobLogVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 定时任务调度日志信息接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface ISysJobLogService {

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param query 调度日志信息
     * @return 调度任务日志集合
     */
    ResponseInfo<IPageInfo<SysJobLogVO>> pageJobLog(SysJobQry query);

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param query 调度日志信息
     * @return 调度任务日志集合
     */
    ResponseInfo<List<SysJobLogVO>> listJobLog(SysJobQry query);

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    ResponseInfo<SysJobLogVO> getJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     * @return
     */
    ResponseInfo saveJobLog(SysJobLog jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的日志ID
     * @return 结果
     */
    ResponseInfo removeJobLogByIds(List<Long> logIds);

    /**
     * 清空任务日志
     *
     * @return
     */
    ResponseInfo cleanJobLog();

}
