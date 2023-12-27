package com.master.chat.controller.quartz;

import com.master.chat.common.util.ExcelUtil;
import com.master.chat.framework.base.BaseController;
import com.master.chat.framework.web.exception.TaskException;
import com.master.chat.quartz.constant.QuartzConstants;
import com.master.chat.quartz.pojo.command.SysJobCommand;
import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.dto.SysJobExcelDTO;
import com.master.chat.quartz.pojo.dto.SysJobLogExcelDTO;
import com.master.chat.quartz.pojo.vo.SysJobLogVO;
import com.master.chat.quartz.pojo.vo.SysJobVO;
import com.master.chat.quartz.service.ISysJobLogService;
import com.master.chat.quartz.service.ISysJobService;
import com.master.chat.quartz.util.CronUtils;
import com.master.chat.quartz.util.ScheduleUtils;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.common.annotation.Log;
import com.master.common.api.IPageInfo;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.HttpConstant;
import com.master.common.enums.BusinessTypeEnum;
import com.master.common.utils.DozerUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 调度任务信息 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/job")
public class SysJobController extends BaseController {
    @Autowired
    private ISysJobService jobService;
    @Autowired
    private ISysJobLogService jobLogService;

    /**
     * 查询定时任务列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('monitor:job:list')")
    public ResponseInfo<IPageInfo<SysJobVO>> pageJob(SysJobQry query) {
        return jobService.pageJob(query);
    }

    /**
     * 获取定时任务详细信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('monitor:job:query')")
    public ResponseInfo getJobById(@PathVariable("id") Long id) {
        return jobService.getJobById(id);
    }

    /**
     * 新增定时任务
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('monitor:job:save')")
    @Log(type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo add(@RequestBody SysJobCommand command) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(command.getCronExpression())) {
            return ResponseInfo.error("新增任务'" + command.getName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(command.getInvokeTarget(), QuartzConstants.LOOKUP_RMI)) {
            return ResponseInfo.error("新增任务'" + command.getName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(command.getInvokeTarget(), new String[]{QuartzConstants.LOOKUP_LDAP, QuartzConstants.LOOKUP_LDAPS})) {
            return ResponseInfo.error("新增任务'" + command.getName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(command.getInvokeTarget(), new String[]{HttpConstant.HTTP, HttpConstant.HTTPS})) {
            return ResponseInfo.error("新增任务'" + command.getName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(command.getInvokeTarget(), QuartzConstants.JOB_ERROR_STR)) {
            return ResponseInfo.error("新增任务'" + command.getName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(command.getInvokeTarget())) {
            return ResponseInfo.error("新增任务'" + command.getName() + "'失败，目标字符串不在白名单内");
        }
        return jobService.insertJob(command);
    }

    /**
     * 修改定时任务
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('monitor:job:update')")
    @Log(type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo edit(@RequestBody SysJobCommand command) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(command.getCronExpression())) {
            return ResponseInfo.error("修改任务'" + command.getName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(command.getInvokeTarget(), QuartzConstants.LOOKUP_RMI)) {
            return ResponseInfo.error("修改任务'" + command.getName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(command.getInvokeTarget(), new String[]{QuartzConstants.LOOKUP_LDAP, QuartzConstants.LOOKUP_LDAPS})) {
            return ResponseInfo.error("修改任务'" + command.getName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(command.getInvokeTarget(), new String[]{HttpConstant.HTTP, HttpConstant.HTTPS})) {
            return ResponseInfo.error("修改任务'" + command.getName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(command.getInvokeTarget(), QuartzConstants.JOB_ERROR_STR)) {
            return ResponseInfo.error("修改任务'" + command.getName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(command.getInvokeTarget())) {
            return ResponseInfo.error("修改任务'" + command.getName() + "'失败，目标字符串不在白名单内");
        }
        return jobService.updateJob(command);
    }

    /**
     * 定时任务状态修改
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/status/change")
    @PreAuthorize("hasAuthority('monitor:job:changeStatus')")
    @Log(value = "启用/禁用定时任务", type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo changeJobStatus(@RequestBody SysJobCommand command) throws SchedulerException {
        return jobService.changeJobStatus(command);
    }

    /**
     * 定时任务立即执行一次
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/run")
    @Log(value = "定时任务", type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo runJob(@RequestBody SysJobCommand command) throws SchedulerException {
        return jobService.runJob(command);
    }

    /**
     * 删除定时任务
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('monitor:job:remove')")
    @Log(type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeJobByIds(@PathVariable List<Long> ids) throws SchedulerException {
        return jobService.deleteJobByIds(ids);
    }

    /**
     * 导出定时任务列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('monitor:job:export')")
    @Log(value = "导出定时任务", type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.EXPORT)
    public void exportJob(HttpServletResponse response, SysJobQry query) throws IOException {
        List<SysJobVO> list = jobService.listJob(query).getData();
        ExcelUtil.write(response, "定时任务列表", SysJobExcelDTO.class, DozerUtil.convertor(list, SysJobExcelDTO.class));
    }

    /**
     * 查询定时任务调度日志列表明
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/log/page")
    public ResponseInfo pageJobLog(SysJobQry qry) {
        qry.assertPage();
        return jobLogService.pageJobLog(qry);
    }

    /**
     * 根据调度编号获取详细信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping(value = "/log/{id}")
    public ResponseInfo getJobLogById(@PathVariable Long id) {
        return jobLogService.getJobLogById(id);
    }

    /**
     * 删除定时任务调度日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/log/{ids}")
    @Log(value = "删除定时任务调度日志", type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeJobLogByIds(@PathVariable List<Long> ids) {
        return jobLogService.removeJobLogByIds(ids);
    }

    /**
     * 清空定时任务调度日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/log/clean")
    @Log(value = "清空定时任务调度日志", type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.CLEAN)
    public ResponseInfo cleanJobLog() {
        return jobLogService.cleanJobLog();
    }

    /**
     * 导出定时任务调度日志列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/log/export")
    @Log(value = "导出定时任务调度日志", type = SysLogTypeConstant.JOB, businessType = BusinessTypeEnum.EXPORT)
    public void exportJobLog(HttpServletResponse response, SysJobQry qry) throws IOException {
        List<SysJobLogVO> list = jobLogService.listJobLog(qry).getData();
        ExcelUtil.write(response, "调度日志列表", SysJobLogExcelDTO.class, DozerUtil.convertor(list, SysJobLogExcelDTO.class));
    }

}
