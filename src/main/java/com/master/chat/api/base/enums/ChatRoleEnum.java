package com.master.chat.api.base.enums;

import lombok.Getter;

/**
 * 聊天角色枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum ChatRoleEnum {

    /**
     * 角色
     */
    SYSTEM("system", "系统"),

    /**
     * 角色
     */
    ASSISTANT("assistant", "角色"),

    USER("user", "用户");

    /**
     * 值
     */
    private final String value;

    /**
     * 标签
     */
    private final String label;

    ChatRoleEnum(final String value, final String label) {
        this.label = label;
        this.value = value;
    }

}
