package com.master.chat.common.annotation;

import java.lang.annotation.*;

/**
 * 验证登录信息
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 1.0.0
 * Copyright Ⓒ 2021 Master Computer Corporation Limited All rights reserved.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {

}
