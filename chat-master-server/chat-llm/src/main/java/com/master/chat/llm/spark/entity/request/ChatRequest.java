package com.master.chat.llm.spark.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.master.chat.llm.spark.enums.ModelEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火请求信息
 *
 * @author: Yang
 * @date: 2024/05/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatRequest implements Serializable {
    private static final long serialVersionUID = 8142547165395379456L;

    private ChatHeader header;

    private ChatParameter parameter;

    private ChatPayload payload;

    @JsonIgnore
    private transient ModelEnum apiVersion = ModelEnum.V2_0;
    public static ChatRequestBuilder builder() {
        return new ChatRequestBuilder();
    }

}
