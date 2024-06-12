package com.master.chat.llm.spark.entity.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火请求参数
 *
 * @author: Yang
 * @date: 2024/05/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatParameter implements Serializable {
    private static final long serialVersionUID = 4502096141480336425L;

    private ChatCompletion chat;

    public ChatParameter() {
    }

    public ChatParameter(ChatCompletion chat) {
        this.chat = chat;
    }

}
