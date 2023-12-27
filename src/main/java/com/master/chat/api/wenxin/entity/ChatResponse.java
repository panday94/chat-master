package com.master.chat.api.wenxin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * https://cloud.baidu.com/doc/WENXINWORKSHOP/s/jlil56u11
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

    private ChatUsage usage;
}
