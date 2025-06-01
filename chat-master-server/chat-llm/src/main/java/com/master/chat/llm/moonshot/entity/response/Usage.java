package com.master.chat.llm.moonshot.entity.response;

import lombok.Data;

/**
 * 使用量
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
public class Usage {

    /**
     * 提示词token
     */
    private int promptTokens;

    /**
     * 输出token
     */
    private int completionTokens;

    /**
     * 总token
     */
    private int totalTokens;

}
