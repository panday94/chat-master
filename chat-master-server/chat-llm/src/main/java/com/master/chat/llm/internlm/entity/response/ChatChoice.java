package com.master.chat.llm.internlm.entity.response;

import com.google.gson.annotations.SerializedName;
import com.master.chat.llm.internlm.entity.request.ChatCompletionMessage;
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
public class ChatChoice {


    private int index;

    /**
     * 对话内容
     */
    private ChatCompletionMessage message;

    /**
     * 结束原因
     */
    @SerializedName("finish_reason")
    private String finishReason;

}
