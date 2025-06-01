package com.master.chat.llm.deepseek.enmus;

import lombok.Getter;

/**
 * 模型枚举
 * @author: Yang
 * @date: 2025/2/17
 * @version: 1.2.4
 * Copyright Ⓒ 2024 熊扬软件开发工作室 Computer Corporation Limited All rights reserved.
 */
@Getter
public enum Model {

    /**
     * deepseek-reasoner 代表R1模型
     */
    CHAT("deepseek-chat"),

    REASONER("deepseek-reasoner"),

    ;
    private final String name;

    Model(String name) {
        this.name = name;
    }

}
