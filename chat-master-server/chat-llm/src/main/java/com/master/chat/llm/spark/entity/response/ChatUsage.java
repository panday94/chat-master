package com.master.chat.llm.spark.entity.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 使用量
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
public class ChatUsage implements Serializable {
    private static final long serialVersionUID = 2181817132625461079L;

    /**
     * 使用量
     */
    private Usage text;

}
