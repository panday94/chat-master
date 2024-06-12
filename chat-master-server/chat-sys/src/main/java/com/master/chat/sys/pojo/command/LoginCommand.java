package com.master.chat.sys.pojo.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class LoginCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 验证码
     */
    private String code;

    /**
     * 分享码
     */
    private String shareCode;

    /**
     * 验证码绑定uuid
     */
    private String uuid;

    /**
     * 登陆类型 1 微信小程序 2 微信公众号 3 手机号 4 账户密码登录
     */
    private Integer loginType;

}
