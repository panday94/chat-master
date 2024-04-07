package com.master.chat.api.moonshot.entity;

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
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest {

    /***
     * 模型
     */
    public String model;

    /**
     * 消息
     */
    public List<ChatCompletionMessage> messages;

    @SerializedName("max_tokens")
    public int maxTokens;

    @SerializedName("temperature")
    public float temperature;
    public float topP;

    public Integer n;
    public boolean stream = false;
    public List<String> stop;

    @SerializedName("presence_penalty")
    public float presencePenalty;

    @SerializedName("frequency_penalty")
    public float frequencyPenalty;

    public String user;


    public ChatCompletionRequest(String model, List<ChatCompletionMessage> messages, int maxTokens, float temperature, int n) {
        this.model = model;
        this.messages = messages;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.n = n;
    }

}
