package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 环境
 *
 * @author: Yang
 * @date: 2020/8/6
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum SpringProfilesEnum {

    /**
     * value
     */
    DEV("dev"), TEST("test"), PROD("prod");

    private final String value;

    /**
     * 带value的构造方法
     *
     * @param value
     */
    SpringProfilesEnum(final String value) {
        this.value = value;
    }

}
