package com.master.chat.common.xss;

import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * SQL过滤
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 3.0.0
 * Copyright Ⓒ 2021 Master Computer Corporation Limited All rights reserved.
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", StringPoolConstant.EMPTY);
        str = StringUtils.replace(str, "\"", StringPoolConstant.EMPTY);
        str = StringUtils.replace(str, ";", StringPoolConstant.EMPTY);
        str = StringUtils.replace(str, "\\", StringPoolConstant.EMPTY);

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
                "drop"};
        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.indexOf(keyword) != -1) {
                throw new BusinessException("包含非法字符");
            }
        }
        return str;
    }

}
