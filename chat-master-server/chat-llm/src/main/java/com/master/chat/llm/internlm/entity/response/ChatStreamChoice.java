package com.master.chat.llm.internlm.entity.response;

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
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatStreamChoice {

    private int index;

    /**
     * 返回内容
     */
    private ChatStreamChoiceDelta message;

    /**
     * 结束原因
     */
    @SerializedName("finish_reason")
    private String finishReason;

}
