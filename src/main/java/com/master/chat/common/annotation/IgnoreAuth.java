package com.master.chat.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略Token验证
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {

}
