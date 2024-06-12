package com.master.chat.framework.util;

import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.framework.validator.ValidatorUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数据工具类
 *
 * @author: Yang
 * @date: 2020/12/24
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public class NumberUtil {

    private static final String[] HAN_ARR = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] UNIT_ARR = {"十", "百", "千", "万", "十", "白", "千", "亿", "十", "百", "千"};
    private static final char[] NUM_ARR = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};

    /**
     * String转成int的值， 若无法转换，默认返回0
     *
     * @param string 字符串
     */
    public static int stoi(String string) {
        return stoi(string, 0);
    }

    public static int stoi(String string, int defaultValue) {
        if (ValidatorUtil.isNull(string)) {
            return defaultValue;
        }
        int id;
        try {
            id = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
        return id;
    }

    /**
     * String转成long的值， 若无法转换，默认返回0
     *
     * @param string 字符串
     */
    public static long stol(String string) {
        return stol(string, 0);
    }

    public static long stol(String string, long defaultValue) {
        if (ValidatorUtil.isNull(string)) {
            return defaultValue;
        }
        long ret;
        try {
            ret = Long.parseLong(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }

        return ret;
    }

    /**
     * String转成double的值， 若无法转换，默认返回0.00
     *
     * @param string 字符串
     * @return
     */
    public static double stod(String string) {
        return stod(string, 0.00);
    }

    public static double stod(String string, double defaultValue) {
        if (ValidatorUtil.isNull(string)) {
            return defaultValue;
        }
        double ret;
        try {
            ret = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }

        return ret;
    }

    /**
     * 将整数转成中文表示
     *
     * @param num 数字
     */
    public static String toChineseNum(int number) {
        String numStr = String.valueOf(number);
        String result = "";
        int numLen = numStr.length();
        for (int i = 0; i < numLen; i++) {
            int num = numStr.charAt(i) - 48;
            if (i != numLen - 1 && num != 0) {
                result += HAN_ARR[num] + UNIT_ARR[numLen - 2 - i];
                if (number >= 10 && number < 20) {
                    result = result.substring(1);
                }
            } else {
                if (!(number >= 10 && number % 10 == 0)) {
                    result += HAN_ARR[num];
                }
            }
        }
        return result;
    }

    /**
     * 阿拉伯数字转中文
     *
     * @param num 数字
     * @return
     */
    public static String numToChinese(int number) {
        char[] val = String.valueOf(number).toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            String m = val[i] + StringPoolConstant.EMPTY;
            int n = Integer.valueOf(m);
            sb.append(NUM_ARR[n]);
        }
        return sb.toString();
    }

    /**
     * 保留两位小数。
     */
    public static Double formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(d));
    }

    /**
     * 保留两位小数。
     */
    public static BigDecimal formatBigDecimal(BigDecimal d) {
        return d.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
