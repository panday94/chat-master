package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 微信应用类型
 *
 * @author: Yang
 * @date: 2023/5/20
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum WxApplicationEnum {

    /**
     * 移动应用
     */
    APP(1),

    /**
     * 网站应用
     */
    WEB(2),

    /**
     * 公众号
     */
    PUBLIC(3),

    /**
     * 小程序
     */
    MINI(4),

    /**
     * 企业微信
     */
    CP(5);

    private final Integer value;


    WxApplicationEnum(final Integer value) {
        this.value = value;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     */
    public static WxApplicationEnum getEnum(Integer value) {
        for (WxApplicationEnum c : WxApplicationEnum.values()) {
            if (c.getValue().equals(value)) {
                return c;
            }
        }
        return null;
    }

}
