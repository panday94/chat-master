package com.master.chat.quartz.pojo.vo;

import com.master.chat.quartz.constant.ScheduleConstants;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class SysJobVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * cron计划策略 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行
     */
    private Integer misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /**
     * 是否并发执行（0禁止 1允许）
     */
    private Integer concurrent;

    /**
     * 任务状态（1正常 0暂停）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
