package com.master.chat.llm.internlm.entity.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 使用token
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
public class ChatTokenData {

    /**
     * 使用token
     */
    @SerializedName("total_tokens")
    public int totalTokens;


}
