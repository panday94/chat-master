package com.master.chat.sys.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 登录日志导出对象
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
public class LoginLogExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "登录id")
    private Long id;

    /**
     * 登录账号
     */
    @ExcelProperty(value = "登录账号")
    private String username;

    /**
     * 登录时间
     */
    @ExcelProperty(value = "登录时间")
    private String createTime;

    /**
     * token过期时间
     */
    @ExcelProperty(value = "token过期时间")
    private String expireTime;

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
     * 登录信息
     */
    @ExcelProperty(value = "登录信息")
    private String msg;

}
