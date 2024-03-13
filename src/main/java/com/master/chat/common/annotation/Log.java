package com.master.chat.common.annotation;

import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.common.enums.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * 系统日志 @SysLog(type = "1",value = "操作内容")
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作类型
     */
    public BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 操作人类别
     */
    public OperatorTypeEnum operatorType() default OperatorTypeEnum.MANAGE;

    /**
     * 模块名
     */
    String type() default StringPoolConstant.EMPTY;

    /**
     * 操作内容
     */
    String value() default StringPoolConstant.EMPTY;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;

}
