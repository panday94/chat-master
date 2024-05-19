package com.master.chat.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略Token验证
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 1.0.0

 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {

}
