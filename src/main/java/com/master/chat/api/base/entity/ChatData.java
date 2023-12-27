package com.master.chat.api.base.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流返回内容
 *
 * @author: yang
 * @date: 2023/11/24
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatData {

    private String id;
    private String conversationId;
    private String parentMessageId;
    private String role;
    private String text;

}
