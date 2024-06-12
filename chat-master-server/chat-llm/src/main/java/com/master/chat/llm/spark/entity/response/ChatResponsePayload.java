package com.master.chat.llm.spark.entity.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 返回体
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatResponsePayload implements Serializable {
    private static final long serialVersionUID = 8090192271782303700L;

    /**
     * 返回内容
     */
    private ChatResponseChoices choices;

    /**
     * 使用信息
     */
    private ChatUsage usage;

}
