package com.master.chat.common.enums;

/**
 * 限流类型
 *
 * @author: Yang
 * @date: 2023/10/20
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public enum LimitTypeEnum {

    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP
}
