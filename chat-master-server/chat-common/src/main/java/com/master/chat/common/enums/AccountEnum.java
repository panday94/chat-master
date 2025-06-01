package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 账号类型枚举
 *
 * @author: Yang
 * @date: 2020/11/17
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Getter
public enum AccountEnum {

    /**
     * 后台用户
     */
    ADMIN(1, "后台用户"),

    /**
     * 前台用户
     */
    USER(2, "前台用户");

    private final Integer value;
    private final String label;

    AccountEnum(final Integer value, final String label) {
        this.value = value;
        this.label = label;
    }

}
