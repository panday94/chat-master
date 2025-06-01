package com.master.chat.llm.internlm.entity.response;

import lombok.Data;

/**
 * 返回内容
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
public class ChatStreamChoiceDelta {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

}
