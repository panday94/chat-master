package com.master.chat.comm.enums;

import lombok.Getter;

/**
 * sms类型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum SmsEnum {

    /**
     *  sms类型
     */
    ALI(1, "阿里云SMS"),

    TECENT(2, "腾讯云SMS");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 标签
     */
    private final String label;

    SmsEnum(final Integer value, final String label) {
        this.label = label;
        this.value = value;
    }

}
