package com.master.chat.common.annotation;

import com.master.chat.common.constant.StringPoolConstant;

import java.lang.annotation.*;

/**
 * 数据过滤
 *
 * @author: Yang
 * @date: 2023/8/11
 * @version: 1.0.0

 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {

    /**
     * 表的别名
     */
    String tableAlias() default StringPoolConstant.EMPTY;

    /**
     * true：没有本部门数据权限，也能查询本人数据
     */
    boolean user() default true;

}
