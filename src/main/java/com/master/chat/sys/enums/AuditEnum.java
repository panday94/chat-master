package com.master.chat.sys.enums;

import lombok.Getter;

/**
 * 审核类型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum AuditEnum {

    /**
     * 审核类型
     */
    COMMON(1, "模块");

    /**
     * 审核类型
     */
    private final Integer type;

    /**
     * 审核模块
     */
    private final String value;

    AuditEnum(final Integer type, final String value) {
        this.type = type;
        this.value = value;
    }

}
