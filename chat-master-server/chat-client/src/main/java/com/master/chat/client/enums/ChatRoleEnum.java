package com.master.chat.client.enums;

import lombok.Getter;

/**
 * 聊天角色枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Getter
public enum ChatRoleEnum {

    /**
     * 角色
     */
    SYSTEM("system", "系统"),

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
