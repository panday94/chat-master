package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author: Yang
 * @date: 2020/8/6
 * @version: 1.0.0

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
