package com.master.chat.llm.moonshot.entity.response;

import lombok.Data;

import java.util.List;

/**
 * stream流式返回
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
public class ChatStreamResponse {

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
    private List<ChatStreamChoice> choices;

}
