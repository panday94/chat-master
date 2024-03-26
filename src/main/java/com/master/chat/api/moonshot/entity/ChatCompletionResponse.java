package com.master.chat.api.moonshot.entity;

import lombok.Data;

import java.util.List;

/**
 * 输出返回
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ChatCompletionResponse {

    private String id;

    private String object;

    private long created;

    /**
     * 模型
     */
    private String model;

    /**
     * 对话内容
     */
    private List<ChatCompletionChoice> choices;

    /**
     * 使用量
     */
    private Usage usage;

    /**
     * 使用量
     */
    private ChatTokenData data;

}
