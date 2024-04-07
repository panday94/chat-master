package com.master.chat.common.annotation;

import com.master.chat.common.constant.RedisConstant;
import com.master.chat.common.enums.LimitTypeEnum;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author: Yang
 * @date: 2023/10/20
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流key
     */
    public String key() default RedisConstant.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    public int time() default 60;

    /**
     * 限流次数
     */
    public int count() default 100;

    /**
     * 限流类型
     */
    public LimitTypeEnum limitType() default LimitTypeEnum.DEFAULT;
}
