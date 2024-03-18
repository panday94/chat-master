package com.master.chat.api.base.entity;

import com.master.chat.api.base.enums.ChatContentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流返回内容
 *
 * @author: Yang
 * @date: 2023/11/24
 * @version: 1.0.0
 * Copyright Ⓒ 2023 MasterComputer Corporation Limited All rights reserved.
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
