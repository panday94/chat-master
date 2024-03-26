package com.master.chat.api.moonshot.entity;

import lombok.Data;

import java.util.List;

/**
 * stream流式返回
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ChatCompletionStreamResponse {

    private String id;

    private String object;

    /**
     * 时间戳
     */
    private long created;

    /**
     * 使用模型
     */
    private String model;

    /**
     * 返回对话内容
     */
    private List<ChatCompletionStreamChoice> choices;

}
