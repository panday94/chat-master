package com.master.chat.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.framework.web.exception.TaskException;
import com.master.chat.quartz.constant.ScheduleConstants;
import com.master.chat.quartz.mapper.SysJobMapper;
import com.master.chat.quartz.pojo.command.SysJobCommand;
import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.entity.SysJob;
import com.master.chat.quartz.pojo.vo.SysJobVO;
import com.master.chat.quartz.service.ISysJobService;
import com.master.chat.quartz.util.ScheduleUtils;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务调度信息接口 实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysJobServiceImpl implements ISysJobService {
    @Autowired
    private SysJobMapper jobMapper;
    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        scheduler.clear();
        List<SysJob> jobList = jobMapper.listJob(new SysJobQry());
        for (SysJob job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    /**
     * 获取定时任务
     *
     * @param id 定时任务id
     * @return
     */
    private SysJob getJob(Long id) {
        SysJob sysJob = jobMapper.selectById(id);
        if (ValidatorUtil.isNull(sysJob)) {
            throw new ValidateException();
        }
        return sysJob;
    }

    /**
     * 获取查询条件
     *
     * @param query 查询条件
     * @return
     */
    private QueryWrapper<SysJob> getQw(Query query) {
        QueryWrapper<SysJob> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), SysJob::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("group")), SysJob::getJobGroup, query.get("group"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), SysJob::getStatus, query.getStatus());
        qw.lambda().orderByDesc(BaseEntity::getId);
        return qw;
    }

    @Override
    public ResponseInfo<IPageInfo<SysJobVO>> pageJob(SysJobQry query) {
        IPage<SysJob> iPage = jobMapper.pageJob(new Page<SysJob>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), SysJobVO.class));
    }

    @Override
    public ResponseInfo<List<SysJobVO>> listJob(SysJobQry query) {
        return ResponseInfo.success(DozerUtil.convertor(jobMapper.listJob(query), SysJobVO.class));
    }

    @Override
    public ResponseInfo<SysJobVO> getJobById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(jobMapper.selectById(id), SysJobVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo insertJob(SysJobCommand command) throws SchedulerException, TaskException {
        SysJob sysJob = DozerUtil.convertor(command, SysJob.class);
        sysJob.setCreateUser(command.getOperater());
        int rows = jobMapper.insert(sysJob);
        if (rows > 0) {
            ScheduleUtils.createScheduleJob(scheduler, sysJob);
        }
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo updateJob(SysJobCommand command) throws SchedulerException, TaskException {
        SysJob sysJob = getJob(command.getId());
        sysJob.setUpdateUser(command.getOperater());
        DozerUtil.convertor(command, sysJob);
        int rows = jobMapper.updateById(sysJob);
        if (rows > 0) {
            updateSchedulerJob(sysJob, command.getJobGroup());
        }
        return ResponseInfo.success(rows);
    }

    /**
     * 更新定时任务
     *
     * @param job
     * @param jobGroup
     * @throws SchedulerException
     * @throws TaskException
     */
    private void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo changeJobStatus(SysJobCommand command) throws SchedulerException {
        SysJob sysJob = getJob(command.getId());
        Long jobId = sysJob.getId();
        String jobGroup = sysJob.getJobGroup();
        sysJob.setStatus(command.getStatus());
        jobMapper.updateById(sysJob);
        if (StatusEnum.ENABLED.getValue().equals(command.getStatus())) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        } else if (StatusEnum.DISABLED.getValue().equals(command.getStatus())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo runJob(SysJobCommand command) throws SchedulerException {
        SysJob sysJob = getJob(command.getId());
        Long jobId = sysJob.getId();
        String jobGroup = sysJob.getJobGroup();
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, sysJob);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
        return ResponseInfo.success();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo deleteJobByIds(List<Long> ids) throws SchedulerException {
        for (Long id : ids) {
            deleteJob(id);
        }
        return ResponseInfo.success();
    }

    /**
     * 删除定时任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    private ResponseInfo deleteJob(Long id) throws SchedulerException {
        SysJob job = getJob(id);
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteById(jobId);
        if (rows > 0) {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return ResponseInfo.success(rows);
    }

}
