package com.master.chat.quartz.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 定时任务日志导出对象
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
public class SysJobLogExcelDTO implements Serializable {

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
     * 日志信息
     */
    @ExcelProperty(value = "日志信息")
    private String jobMessage;

    /**
     * 执行状态（0正常 1失败）
     */
    @ExcelProperty(value = "执行状态（0正常 1失败）")
    private Integer status;

    /**
     * 异常信息
     */
    @ExcelProperty(value = "异常信息")
    private String exceptionInfo;

    /**
     * 开始时间
     */
    @ExcelProperty(value = "开始时间")
    private String startTime;

    /**
     * 停止时间
     */
    @ExcelProperty(value = "停止时间")
    private String stopTime;

}
