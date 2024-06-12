package com.master.chat.llm.wenxin.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回内容
 *
 * @author: Yang
 * @date: 2023/9/7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@ToString
public class ChatResponse implements Serializable {

    private String id;

    private String object;

    private Long created;

    @JsonProperty("sentence_id")
    private Long sentenceId;

    @JsonProperty("isEnd")
    private Boolean is_end;

    @JsonProperty("is_truncated")
    private Boolean isTruncated;

    private String result;

    @JsonProperty("need_clear_history")
    private Boolean needClearHistory;

    @JsonProperty("ban_round")
    private Long ban_round;

    private Usage usage;
}
