package com.master.chat.llm.locallm.chatchat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class ChatResponse implements Serializable {


    private String text;

    private String answer;

    @JsonProperty("message_id")
    private String messageId;

    /**
     * 文档出处
     */
    private List<String> docs;

}
