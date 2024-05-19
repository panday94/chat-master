package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 系统平台枚举类
 *
 * @author: Yang
 * @date: 2020/10/8
 * @version: 3.0.0

 */
@Getter
public enum OSEnum {

    /**
     * 类型：1->苹果手机系统；2->安卓系统；3->windows系统 4->苹果电脑系统 5-> linux系统
     */
    IOS(1, "ios"),

    ANDROID(2, "android"),

    WINDOWS(3, "windows"),

    MACOS(4, "macos"),

    LINUX(5, "linux");

    private final Integer value;

    private final String label;

    /**
     * 构造方法
     *
     * @param code
     * @param value
     */
    private OSEnum(final Integer value, final String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     */
    public static String getValue(Integer value) {
        for (OSEnum c : OSEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.label;
            }
        }
        return null;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     */
    public static Integer getLabel(String label) {
        for (OSEnum c : OSEnum.values()) {
            if (c.getLabel().equals(label)) {
                return c.value;
            }
        }
        return null;
    }

}
