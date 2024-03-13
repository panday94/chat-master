package com.master.chat.common.annotation;

import com.master.chat.common.enums.DataSourceTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义多数据源切换注解
 * 优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准
 *
 * @author: Yang
 * @date: 2021/8/11
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    /**
     * 切换数据源名称
     */
    public DataSourceTypeEnum value() default DataSourceTypeEnum.MASTER;

}
