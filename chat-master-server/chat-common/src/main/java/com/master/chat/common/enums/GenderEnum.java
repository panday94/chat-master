package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 性别枚举
 *
 * @author: Yang
 * @date: 2020/11/17
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum GenderEnum {

    /**
     * 性别女
     */
    FEMALE(0, "女"),

    /**
     * 性别男
     */
    MALE(1, "男");

    private final Integer code;
    private final String value;

    GenderEnum(final Integer code, final String value) {
        this.code = code;
        this.value = value;
    }

}
