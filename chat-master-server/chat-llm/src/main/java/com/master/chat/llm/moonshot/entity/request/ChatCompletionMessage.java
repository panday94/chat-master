package com.master.chat.llm.moonshot.entity.request;

import lombok.Data;

/**
 * 对话内容
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatCompletionMessage {

    /**
     * 角色
     */
    public String role;
    public String name;
    public String content;
    public Boolean partial;

    public ChatCompletionMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public ChatCompletionMessage(String role, String name, String content, Boolean partial) {
        this.role = role;
        this.name = name;
        this.content = content;
        this.partial = partial;
    }

}
