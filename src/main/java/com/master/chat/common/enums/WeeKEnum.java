package com.master.chat.common.enums;

import lombok.Getter;

/**
 * week星期枚举
 *
 * @author: Yang
 * @date: 2021/2/2 15:21
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum WeeKEnum {

    /**
     * 星期一
     */
    MONDAY(1, "MONDAY", "周一"),

    TUESDAY(2, "TUESDAY", "周二"),

    WEDNESDAY(3, "WEDNESDAY", "周三"),

    THURSDAY(4, "THURSDAY", "周四"),

    FRIDAY(5, "FRIDAY", "周五"),

    SATURDAY(6, "SATURDAY", "周六"),

    SUNDAY(7, "SUNDAY", "周日");


    private final Integer real;

    /**
     * 英文
     */
    private final String english;

    /**
     * 中文
     */
    private final String chinese;

    WeeKEnum(final Integer real, final String english, final String chinese) {
        this.real = real;
        this.english = english;
        this.chinese = chinese;
    }

    public static String getWeekChinese(Integer code) {
        for (WeeKEnum i : WeeKEnum.values()) {
            if (i.real.equals(code)) {
                return i.chinese;
            }
        }
        return null;
    }

    public static Integer getWeekReal(String chinese) {
        for (WeeKEnum i : WeeKEnum.values()) {
            if (i.chinese.equals(chinese)) {
                return i.real;
            }
        }
        return null;
    }

}
