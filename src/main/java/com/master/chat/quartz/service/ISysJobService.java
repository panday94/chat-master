package com.master.chat.quartz.service;

import com.master.chat.quartz.pojo.command.SysJobCommand;
import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.vo.SysJobVO;
import com.master.chat.framework.web.exception.TaskException;
import com.master.common.api.IPageInfo;
import com.master.common.api.ResponseInfo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 定时任务调度信息信息接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ISysJobService {

    /**
     * 获取quartz调度器的计划任务
     *
     * @param query 查询条件
     * @return 调度任务集合
     */
    ResponseInfo<IPageInfo<SysJobVO>> pageJob(SysJobQry quer);

    /**
     * 获取quartz调度器的计划任务
     *
     * @param query 查询条件
     * @return 调度任务集合
     */
    ResponseInfo<List<SysJobVO>> listJob(SysJobQry query);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param id 调度任务ID
     * @return 调度任务对象信息
     */
    ResponseInfo<SysJobVO> getJobById(Long id);


    /**
     * 新增任务
     *
     * @param command 调度信息
     * @return 结果
     * @throws SchedulerException
     * @throws TaskException
     */

    ResponseInfo insertJob(SysJobCommand command) throws SchedulerException, TaskException;

    /**
     * 更新任务
     *
     * @param command 调度信息
     * @return 结果
     * @throws SchedulerException
     * @throws TaskException
     */
    ResponseInfo updateJob(SysJobCommand command) throws SchedulerException, TaskException;

    /**
     * 任务调度状态修改
     *
     * @param command 调度信息
     * @return 结果
     * @throws SchedulerException
     */
    ResponseInfo changeJobStatus(SysJobCommand command) throws SchedulerException;

    /**
     * 立即运行任务
     *
     * @param command 调度信息
     * @return 结果
     * @throws SchedulerException
     */
    ResponseInfo runJob(SysJobCommand command) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param ids 需要删除的任务ID
     * @return 结果
     * @throws SchedulerException
     */
    ResponseInfo deleteJobByIds(List<Long> ids) throws SchedulerException;

}
