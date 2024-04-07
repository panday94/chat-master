package com.master.chat.api.moonshot.entity;

import lombok.Data;

/**
 * 返回内容
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
