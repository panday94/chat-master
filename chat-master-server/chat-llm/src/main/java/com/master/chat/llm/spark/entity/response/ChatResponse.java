package com.master.chat.llm.spark.entity.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 响应
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatResponse implements Serializable {
    private static final long serialVersionUID = 886720558849587945L;

    private ChatResponseHeader header;

    private ChatResponsePayload payload;

}
