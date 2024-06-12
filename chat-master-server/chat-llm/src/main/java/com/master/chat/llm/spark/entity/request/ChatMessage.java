package com.master.chat.llm.spark.entity.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 讯飞星火请求消息信息
 *
 * @author: Yang
 * @date: 2024/05/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 6725091574720504980L;

    private List<ChatCompletionMessage> text;

    public ChatMessage() {
    }

    public ChatMessage(List<ChatCompletionMessage> text) {
        this.text = text;
    }

}
