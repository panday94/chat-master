package com.master.chat.llm.internlm.entity.response;

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
public class ChatStreamResponse {

    /**
     * 回复唯一标识
     */
    private String id;

    /**
     * 时间戳
     */
    private Long created;

    /**
     * 使用模型
     */
    private String model;

    /**
     * 返回对话内容
     */
    private List<ChatStreamChoice> choices;

}
