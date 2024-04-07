package com.master.chat.api.moonshot.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 使用token
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatTokenData {

    /**
     * 使用token
     */
    @SerializedName("total_tokens")
    public int totalTokens;


}
