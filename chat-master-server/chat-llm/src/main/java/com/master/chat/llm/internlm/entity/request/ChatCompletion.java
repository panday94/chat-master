package com.master.chat.llm.internlm.entity.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 对话请求
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletion {

    /***
     * 模型
     */
    public String model;

    /**
     * 消息
     */
    public List<ChatCompletionMessage> messages;

    @SerializedName("temperature")
    public float temperature;

    @SerializedName("top_p")
    public float topP;

    @SerializedName("request_output_len")
    public Integer requestOutputLen;

    public boolean stream = false;

}
