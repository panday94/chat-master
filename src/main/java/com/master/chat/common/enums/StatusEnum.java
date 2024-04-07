package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author: Yang
 * @date: 2020/8/6
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum StatusEnum {

    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 启用
     */
    ENABLED(1);

    private final Integer value;

    StatusEnum(final Integer value) {
        this.value = value;
    }

}
