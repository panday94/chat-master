package com.master.chat.api.moonshot.entity;

import lombok.Data;

/**
 * 返回内容
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ChatCompletionStreamChoiceDelta {

    /**
     * 内容
     */
    private String content;

    /**
     * 角色
     */
    private String role;

}
