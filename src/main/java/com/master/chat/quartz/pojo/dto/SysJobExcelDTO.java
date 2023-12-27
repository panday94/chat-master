package com.master.chat.quartz.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 定时任务导出对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Master Computer Corporation Limited All rights reserved.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SysJobExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "角色编号")
    private Long id;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String createTime;

    /**
     * 任务名称
     */
    @ExcelProperty(value = "任务名称")
    private String name;

    /**
     * 任务组名
     */
    @ExcelProperty(value = "任务组名")
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @ExcelProperty(value = "调用目标字符串")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @ExcelProperty(value = "cron执行表达式")
    private String cronExpression;

}
