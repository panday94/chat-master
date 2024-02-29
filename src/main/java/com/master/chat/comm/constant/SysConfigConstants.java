package com.master.chat.comm.constant;

/**
 * 系统配置常量
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface SysConfigConstants {

    /**
     * 验证码开关
     */
    String CAPTCHA_ON_OFF = "sys.account.captchaOnOff";

    /**
     * 注册开关
     */
    String REGISTER_ON_OFF = "sys.account.registerUser";

    /**
     * 是否开启同时登录
     */
    String ALL_LOGIN = "sys.account.allLogin";

    /**
     * 是否限制访问ChatMaster开关
     */
    String CHAT_MASTER_ON_OFF = "sys.chat.master";

}
