package com.master.chat.quartz.util;

import com.master.chat.quartz.constant.ScheduleConstants;
import com.master.chat.quartz.pojo.entity.SysJob;
import com.master.chat.quartz.pojo.entity.SysJobLog;
import com.master.chat.quartz.service.ISysJobLogService;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.utils.ApplicationContextUtil;
import com.master.chat.common.utils.DateUtil;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJob sysJob = new SysJob();
        DozerUtil.convertor(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
        try {
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setName(sysJob.getName());
        sysJobLog.setJobId(sysJob.getId());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(DateUtil.parseLocalDateTime(DateUtil.formatDate(startTime)));
        sysJobLog.setStopTime(LocalDateTime.now());
        long runMs = DateUtil.intervalMillis(sysJobLog.getStartTime(), sysJobLog.getStopTime());
        sysJobLog.setJobMessage(sysJobLog.getName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus(StatusEnum.DISABLED.getValue());
            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus(StatusEnum.ENABLED.getValue());
        }

        // 写入数据库当中
        ApplicationContextUtil.getBean(ISysJobLogService.class).saveJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;

}
