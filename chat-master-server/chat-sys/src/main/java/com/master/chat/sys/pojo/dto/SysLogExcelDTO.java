package com.master.chat.sys.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 系统操作日志导出对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SysLogExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "日志id")
    private Long id;

    /**
     * 操作时间
     */
    @ExcelProperty(value = "操作时间")
    private String createTime;

    /**
     * 操作人
     */
    @ExcelProperty(value = "操作人")
    private String username;

    /**
     * 请求ip
     */
    @ExcelProperty(value = "请求ip")
    private String ip;

    /**
     * 登录地址
     */
    @ExcelProperty(value = "登录地址")
    private String address;

    /**
     * 域名
     */
    @ExcelProperty(value = "域名")
    private String domain;

    /**
     * 浏览器类型
     */
    @ExcelProperty(value = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @ExcelProperty(value = "操作系统")
    private String os;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法")
    private String method;

    /**
     * 请求方式
     */
    @ExcelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 接口名称
     */
    @ExcelProperty(value = "接口名称")
    private String uri;

    /**
     * 操作内容
     */
    @ExcelProperty(value = "操作内容")
    private String operation;

    /**
     * 系统模块
     */
    @ExcelProperty(value = "系统模块")
    private String title;

    /**
     * 请求耗时
     */
    @ExcelProperty(value = "请求耗时(毫秒)")
    private Long time;

}
