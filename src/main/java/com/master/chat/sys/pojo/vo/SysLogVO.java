package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志返回对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Master Computer Corporation Limited All rights reserved.
 */
@Data
public class SysLogVO implements Serializable {

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
     * 操作人
     */
    private String username;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 登录地址
     */
    private String address;

    /**
     * 域名
     */
    private String domain;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 接口名称
     */
    private String uri;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 系统模块
     */
    private String title;

    /**
     * 业务类型 {@link com.master.common.enums.BusinessTypeEnum}
     */
    private String businessType;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行结果
     */
    private String result;

}
