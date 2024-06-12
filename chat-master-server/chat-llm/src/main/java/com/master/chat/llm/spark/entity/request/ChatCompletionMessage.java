package com.master.chat.llm.spark.entity.request;

import com.master.chat.client.enums.ChatRoleEnum;
import lombok.Data;

/**
 * 讯飞星火 消息
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatCompletionMessage {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

    /**
     * 索引
     */
    private Integer index;

    /**
     * 创建用户消息
     *
     * @param content 内容
     */
    public static ChatCompletionMessage userContent(String content) {
        return new ChatCompletionMessage(ChatRoleEnum.USER.getValue(), content);
    }

    /**
     * 创建机器人消息
     *
     * @param content 内容
     */
    public static ChatCompletionMessage assistantContent(String content) {
        return new ChatCompletionMessage(ChatRoleEnum.ASSISTANT.getValue(), content);
    }

    public ChatCompletionMessage() {
    }

    public ChatCompletionMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

}
