package com.master.chat.api.moonshot.entity;

import lombok.Data;

import java.util.List;

/**
 * stream流式返回
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
