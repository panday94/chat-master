package com.master.chat.llm.spark.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 使用量信息
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class Usage implements Serializable {
    private static final long serialVersionUID = 8295933047877077971L;

    /**
     * 包含历史问题的总tokens大小(提问tokens大小)
     */
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;

    /**
     * 回答的tokens大小
     */
    @JsonProperty("completion_tokens")
    private Integer completionTokens;

    /**
     * prompt_tokens和completion_tokens的和，也是本次交互计费的tokens大小
     */
    @JsonProperty("total_tokens")
    private Integer totalTokens;

    /**
     * 保留字段，可忽略
     */
    @JsonProperty("question_tokens")
    private Integer questionTokens;

}
