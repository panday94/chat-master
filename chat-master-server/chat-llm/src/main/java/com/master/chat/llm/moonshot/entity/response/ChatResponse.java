package com.master.chat.llm.moonshot.entity.response;

import lombok.Data;

import java.util.List;

/**
 * 输出返回
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatResponse {

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
    private List<ChatChoice> choices;

    /**
     * 使用量
     */
    private Usage usage;

    /**
     * 使用量
     */
    private ChatTokenData data;

}
