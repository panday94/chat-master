package com.master.chat.gpt.pojo.command;

import com.master.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;

/**
 *  会员用户对象 Command
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class UserCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * openid
     */
    private String openid;

    /**
     * unionid
     */
    private String unionid;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 是否开启上下文
     */
    private Boolean context;

    /**
     * 调用次数
     */
    private Long num;

    /**
     * 邀请人
     */
    private Long shareId;

    /**
     * 用户类型 1 微信小程序 2 公众号 3 手机号
     */
    private Integer type;

}
