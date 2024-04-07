package com.master.chat.api.xfyun.entity;

import com.master.chat.api.base.enums.ChatRoleEnum;
import lombok.Data;

/**
 * 讯飞星火 消息
 * *
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class SparkMessage {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

    /**
     * 响应时独有，请求入参请忽略
     */
    private String index;

    /**
     * 创建用户消息
     *
     * @param content 内容
     */
    public static SparkMessage userContent(String content) {
        return new SparkMessage(ChatRoleEnum.USER.getValue(), content);
    }

    /**
     * 创建机器人消息
     *
     * @param content 内容
     */
    public static SparkMessage assistantContent(String content) {
        return new SparkMessage(ChatRoleEnum.ASSISTANT.getValue(), content);
    }

    public SparkMessage() {
    }

    public SparkMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

}
