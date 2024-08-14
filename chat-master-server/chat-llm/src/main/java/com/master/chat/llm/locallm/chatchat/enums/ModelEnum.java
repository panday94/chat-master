package com.master.chat.llm.locallm.chatchat.enums;

import lombok.Getter;

/**
 * Langchain 对话模型类型
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum ModelEnum {

    /**
     * 对话模型类型
     */
//    LLM("llm模型对话", "/chat/chat"),
    LLM("llm模型对话", "/v1/chat/completions"),

    KNOWLEDGE("知识库对话", "/chat/knowledge_base_chat"),

    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 标签
     */
    private final String url;

    ModelEnum(final String value, final String url) {
        this.value = value;
        this.url = url;
    }

}
