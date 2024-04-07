package com.master.chat.gpt.enums;

import lombok.Getter;

/**
 * 审核类型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum UserTypeEnum {

    /**
     *  1 微信小程序 2 公众号 3 手机号 4 账户密码
     */
    WXAPP(1, "微信小程序"),

    WXMP(2, "微信公众号"),

    TEL(3, "手机号"),

    USERNAME(4, "账户密码");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 标签
     */
    private final String label;

    UserTypeEnum(final Integer value, final String label) {
        this.label = label;
        this.value = value;
    }

}
