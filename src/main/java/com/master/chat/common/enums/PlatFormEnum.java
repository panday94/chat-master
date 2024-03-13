package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 运行平台枚举
 *
 * @author: Yang
 * @date: 2020/10/8
 * @version: 3.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum PlatFormEnum {

    /**
     * 类型：1-> web; 2->H5; 3->App；4->wechat；5->tiktok-cn；6->alipay；7->baidu；
     */
    WEB(1, "H5"),

    H5(2, "H5"),

    APP(3, "App"),

    WECHAT(4, "wechat"),

    TIKTOK_CN(5, "tiktok-cn"),

    ALIPAY(6, "alipay"),

    BAIDU(7, "baidu");

    private final Integer value;

    private final String label;

    /**
     * 构造方法
     *
     * @param code
     * @param value
     */
    private PlatFormEnum(final Integer value, final String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     */
    public static String getLabel(Integer value) {
        for (PlatFormEnum c : PlatFormEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.label;
            }
        }
        return null;
    }

    /**
     * 普通方法
     *
     * @param value
     * @return
     */
    public static Integer getValue(String label) {
        for (PlatFormEnum c : PlatFormEnum.values()) {
            if (c.getLabel().equals(label)) {
                return c.value;
            }
        }
        return null;
    }

}
