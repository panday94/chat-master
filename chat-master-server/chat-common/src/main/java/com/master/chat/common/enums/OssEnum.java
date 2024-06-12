package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 上传类型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum OssEnum {

    /**
     *  上传类型
     */
    LOCAL(1, "本地"),

    ALI(2, "阿里云"),

    TECENT(3, "腾讯云");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 标签
     */
    private final String label;

    OssEnum(final Integer value, final String label) {
        this.label = label;
        this.value = value;
    }

}
