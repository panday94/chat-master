package com.master.chat.common.enums;

import lombok.Getter;

/**
 * sms类型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum SmsEnum {

    /**
     *  sms类型
     */
    NONE(0, "无"),

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
