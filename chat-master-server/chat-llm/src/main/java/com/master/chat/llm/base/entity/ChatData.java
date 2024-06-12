package com.master.chat.llm.base.entity;

import com.master.chat.client.enums.ChatContentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流返回内容
 *
 * @author: Yang
 * @date: 2023/11/24
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatData {

    /**
     * 会话id
     */
    private String id;

    /**
     * 当前消息id
     */
    private String conversationId;

    /**
     * 回复消息id
     */
    private String parentMessageId;

    /**
     * 角色 system、user、assistant
     */
    private String role;

    /**
     * 内容
     */
    private Object content;

    /**
     * 是否输出结束
     */
    private Boolean finish;

    /**
     * 消息类型 text、image
     */
    @Builder.Default
    private String contentType = ChatContentEnum.TEXT.getValue();

}
