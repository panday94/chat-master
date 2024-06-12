package com.master.chat.framework.validator.base;

import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.framework.validator.ValidatorUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 数据校验返回错误提示
 *
 * @author: Yang
 * @date: 2019/8/16
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public abstract class BaseAssert {

    /**
     * 判断参数是否存在
     *
     * @param object  对象
     * @param message 返回提示
     */
    public static void isBlankOrNull(Object object, String... message) {
        if (ValidatorUtil.isNull(object)) {
            throw new ValidateException(ValidatorUtil.isNotNullIncludeArray(message) ? message[0] : ResponseEnum.BAD_REQUEST.getMsg());
        }
    }

    /**
     * 判断参数是否存在(过滤0)
     *
     * @param object  对象
     * @param message 返回提示
     */
    public static void isBlankOrNullOrZero(Object object, String... message) {
        if (ValidatorUtil.isNullOrZero(object)) {
            throw new ValidateException(ValidatorUtil.isNotNullIncludeArray(message) ? message[0] : ResponseEnum.BAD_REQUEST.getMsg());
        }
    }

    /**
     * 判断参数是否为0或者小于0
     *
     * @param param
     */
    public static void isZero(BigDecimal param) {
        if (param.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidateException("参数不能小于0");
        }
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return String
     */
    public static String getParam(Map<String, Object> maps, String param, String... message) {
        isBlankOrNull(maps.get(param), ValidatorUtil.isNotNullIncludeArray(message) ? message[0] : ResponseEnum.BAD_REQUEST.getMsg());
        return maps.get(param).toString();
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return Integer
     */
    public static Integer getParamInt(Map<String, Object> maps, String param, String... message) {
        return Integer.valueOf(getParam(maps, param, message));
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return Long
     */
    public static Long getParamLong(Map<String, Object> maps, String param, String... message) {
        return Long.valueOf(getParam(maps, param, message));
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return Integer
     */
    public static Long getParamId(Map<String, Object> maps) {
        return Long.valueOf(getParam(maps, "id"));
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return Double
     */
    public static Double getParamDouble(Map<String, Object> maps, String param, String... message) {
        return Double.valueOf(getParam(maps, param, message));
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return Boolean
     */
    public static Boolean getParamBoolean(Map<String, Object> maps, String param, String... message) {
        return Boolean.valueOf(getParam(maps, param, message));
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return BigDecimal
     */
    public static BigDecimal getParamBigDecimal(Map<String, Object> maps, String param, String... message) {
        return new BigDecimal(getParam(maps, param, message));
    }

    /**
     * 获取请求体中指定参数
     *
     * @param maps    map结构对象
     * @param param   参数
     * @param message 为null时返回提示
     * @return Boolean
     */
    public static String getParamString(Map<String, String> maps, String param, String... message) {
        isBlankOrNull(maps.get(param), ValidatorUtil.isNotNullIncludeArray(message) ? message[0] : ResponseEnum.BAD_REQUEST.getMsg());
        return maps.get(param);
    }

    /**
     * 判断参数是否存在否则返回默认空字符串
     *
     * @param maps  map结构对象
     * @param param 参数
     */
    public static String getParamOrElse(Map<String, Object> maps, String param) {
        if (ValidatorUtil.isNull(maps.get(param))) {
            return StringPoolConstant.EMPTY;
        }
        return maps.get(param).toString();
    }

    /**
     * 判断参数是否存在否则返回指定字符串
     *
     * @param maps  map结构对象
     * @param param 参数
     * @param other 返回参数
     */
    public static String getParamOrElse(Map<String, Object> maps, String param, String other) {
        if (ValidatorUtil.isNull(maps.get(param))) {
            return other;
        }
        return maps.get(param).toString();
    }

}
