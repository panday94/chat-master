package com.master.chat.quartz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.quartz.constant.ScheduleConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 定时任务调度
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_job")
public class SysJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
