package com.master.chat.api.moonshot.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 使用token
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ChatTokenData {

    /**
     * 使用token
     */
    @SerializedName("total_tokens")
    public int totalTokens;


}
