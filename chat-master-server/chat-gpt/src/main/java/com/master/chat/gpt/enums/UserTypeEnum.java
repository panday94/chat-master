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
    WXAPP(1, "微信小程序", "wxapp:"),

    WXMP(2, "微信公众号", "wxmp:"),

    TEL(3, "手机号", "tel:"),

    USERNAME(4, "账户密码", "username:");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 标签
     */
    private final String label;

    /**
     * 前缀
     */
    private final String prefix;

    UserTypeEnum(final Integer value, final String label, final String prefix) {
        this.label = label;
        this.value = value;
        this.prefix = prefix;
    }

}
