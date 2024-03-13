package com.master.chat.common.validator;

import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.common.utils.NumberUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * hibernate-validator校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author: Yang
 * @date: 2019/8/16
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class ValidatorUtil {
    private static Validator validator;
    private static Pattern pattern;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws RRException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BusinessException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new ValidateException(msg.toString());
        }
    }

    /**
     * 判断内容是空
     *
     * @param str
     * @return
     */
    public static boolean isNull(Object str) {
        return !isNotNull(str);
    }

    /**
     * 判断内容不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(Object str) {
        if (str != null && str.toString().trim().length() > 0 && !StringPoolConstant.NULL.equals(str + StringPoolConstant.EMPTY)
                && !StringPoolConstant.UNDEFINED.equals(str + StringPoolConstant.EMPTY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断内容是空(包含0)
     *
     * @param str
     * @return
     */
    public static boolean isNullOrZero(Object str) {
        return !isNotNullAndZero(str);
    }

    /**
     * 判断内容不为空(包含0)
     *
     * @param str
     * @return
     */
    public static boolean isNotNullAndZero(Object str) {
        if (str != null && str.toString().trim().length() > 0 && !StringPoolConstant.NULL.equals(str + StringPoolConstant.EMPTY)
                && !StringPoolConstant.UNDEFINED.equals(str + StringPoolConstant.EMPTY) && !Double.valueOf(0).equals(Double.valueOf(str.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查对象是否为空（包含数组）
     *
     * @param str
     * @return
     */
    public static boolean isNotNullIncludeArray(Object obj) {
        return !isNullIncludeArray(obj);
    }

    /**
     * 检查对象是否为空（包含数组）
     *
     * @param obj 要检查的数据(数据类型: String、Number、Boolean、Collection、Map、Object[])
     * @return true: 为空; false: 不为空 <li>String：值为 null、""、"0" 时返回 true <li>
     * Number：值为 null、0 时返回 true <li>Boolean：值为 null、false 时返回 true <li>
     * Collection：值为 null、size=0 时返回 true <li>Map：值为 null、size=0 时返回
     * true <li>Object[]：值为 null、length=0 时返回 true
     */
    public static boolean isNullIncludeArray(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && (StringPoolConstant.EMPTY.equals(obj))) {
            return true;
        } else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
            return true;
        } else if (obj instanceof Boolean && !((Boolean) obj)) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是合法邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regex = "^\\w+([\\-+.]\\w+)*@\\w+([-.]\\w+)*\\.[a-z]{2,3}";
        pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    /**
     * 只包含英文字母和数字、下划线
     *
     * @param str
     * @return
     */
    public static boolean onlyNumAndChar(String str) {
        String regex = "^[a-zA-Z0-9_]+$";
        pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 必须包含字母
     *
     * @param str
     * @return
     */
    public static boolean hasLetterAndNum(String str) {
        String regex = "^(?=.*[a-zA-Z].*).{6,}$";
        pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 是否长度符合
     *
     * @param str
     * @param min 最小
     * @param max 最大
     * @return
     */
    public static boolean lengthBetween(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String regex = "[0-9]*";
        pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是小数
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        String regex = "\\d+\\.\\d+";
        pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 判断数字区间
     *
     * @param str
     * @return
     */
    public static boolean numericRange(Integer num, Integer min, Integer max) {
        if (num < min || num > max) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否日期格式
     *
     * @param str
     * @return
     */
    public static boolean isDate(String str) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        pattern = Pattern.compile(regex);
        Matcher isDate = pattern.matcher(str);
        if (!isDate.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 比较两个Double值
     *
     * @param a 参数A
     * @param b 参数B
     * @return true:少于；false: 不少于
     */
    public static boolean isStockShortage(Double a, Double b) {
        if (NumberUtil.formatDouble(a) >= b) {
            return false;
        }
        return true;
    }

}
