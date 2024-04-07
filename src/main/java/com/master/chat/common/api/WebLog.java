package com.master.chat.common.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Controller层的日志封装类
 *
 * @author: Yang
 * @date: 2020/12/8
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Long spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * 请求身份
     */
    private String token;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 地址
     */
    private String address;

    /**
     * 请求参数
     */
    private String parameter;

    /**
     * 返回结果
     */
    private String result;

}
