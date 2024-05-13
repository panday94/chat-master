package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 应用枚举
 *
 * @author: Yang
 * @date: 2023/1/15
 * @version: 1.0.0

 */
@Getter
public enum AppClientEnum {

    /**
     * 锤子OA
     */
    HAMMER("SPTVbFt6bIQtKiB8o7", "锤子OA"),

    /**
     * Master科技
     */
    HXIANG("Hxiang84a4ml9bxi9", "Master科技"),

    /**
     * 订个活
     */
    DINGHUO("SPTp7FtVbIQvKiBqoy", "订个活"),

    /**
     * 公关
     */
    GONGGUAN("CHUAI2hkoB5azgbt91", "公关"),

    /**
     * 订个活招聘
     */
    DINGGEHUO_JOB("CHUAIIbKt2bIbTKicO1", "订个活招聘"),

    /**
     * 同城享购
     */
    TCMALL("SPT15T96lxb62lz8u1", "同城享购"),

    /**
     * 麒麟生鲜
     */
    QLSX("SPT7hww6ny8tqu9vje", "麒麟生鲜"),

    /**
     * 青龙进货
     */
    QLJH("SPT41sB5az3Bdb1u9c", "青龙进货"),

    /**
     * 易企帮
     */
    YQBANG("SPT8hko9cvgbt9mph8", "易企帮"),

    /**
     * 法律帮
     */
    LEGAL("SPTq1ra65azm8obu8v", "法律帮");

    private final String code;

    private final String value;

    AppClientEnum(final String code, final String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     */
    public static String getValue(String code) {
        for (AppClientEnum c : AppClientEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.value;
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
    public static String getCode(String value) {
        for (AppClientEnum c : AppClientEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.code;
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
    public static AppClientEnum getEnum(String code) {
        for (AppClientEnum c : AppClientEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return null;
    }

}
