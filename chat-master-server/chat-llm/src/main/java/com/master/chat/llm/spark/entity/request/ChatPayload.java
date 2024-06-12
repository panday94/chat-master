package com.master.chat.llm.spark.entity.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火请求体
 *
 * @author: Yang
 * @date: 2024/05/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatPayload implements Serializable {
    private static final long serialVersionUID = 2084163918219863102L;

    /**
     * 消息内容
     */
    private ChatMessage message;

    public ChatPayload() {
    }

    public ChatPayload(ChatMessage message) {
        this.message = message;
    }

}
