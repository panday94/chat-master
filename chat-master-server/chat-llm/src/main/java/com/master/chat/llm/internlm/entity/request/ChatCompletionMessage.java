package com.master.chat.llm.internlm.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对话内容
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionMessage {

    /**
     * 角色
     */
    public String role;

    /**
     * 内容
     */
    public String text;

}
