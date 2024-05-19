package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志返回对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class LoginLogVO implements Serializable {

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
     * 登录时间
     */
    private LocalDateTime createTime;

    /**
     * token过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 会话标识
     */
    private String sessionId;

    /**
     * 用户名
     */
    private String username;

    /**
     * ip地址
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
     * 登录信息
     */
    private String msg;

    /**
     * 身份标识
     */
    private String authorization;

    /**
     * 浏览器类型
     */
    private String userAgent;

    /**
     * 登录状态 0 失败 1 成功
     */
    private Integer status;

}
