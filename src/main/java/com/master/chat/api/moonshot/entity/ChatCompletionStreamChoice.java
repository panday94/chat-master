package com.master.chat.api.moonshot.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回对话内容
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionStreamChoice {

    private int index;

    /**
     * 返回内容
     */
    private ChatCompletionStreamChoiceDelta delta;

    /**
     * 结束原因
     */
    @SerializedName("finish_reason")
    private String finishReason;

    /**
     * 使用量
     */
    private Usage usage;

}
