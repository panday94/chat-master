package com.master.chat.llm.locallm.chatchat.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.master.chat.client.model.dto.ChatMessageDTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Slf4j
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChatCompletion implements Serializable {

//    private String query;
    private List<ChatMessageDTO> messages;

    @JsonProperty("conversation_id")
    private String conversationId;

//    @JsonProperty("history_len")
//    private Integer historyLen = -1;

    @JsonProperty("presence_penalty")
    private Double presencePenalty =1.2;
    @JsonProperty("top_p")
    private Double topP = 0.3;

//    @JsonProperty("knowledge_base_name")
//    private String knowledgeBaseName;

//    @JsonProperty("top_k")
//    private Integer topK = 3;

//    @JsonProperty("top_p")
//    private Integer topP = 3;

//    @JsonProperty("score_threshold")
//    private Integer scoreThreshold = 1;

    private List<ChatCompletionMessage> history;

    private Boolean stream = false;

//    private String modelName;
    private String model;

    private Double temperature = 0.9;

    @JsonProperty("max_tokens")
    private Integer maxTokens = 8196;

    @JsonProperty("prompt_name")
    private String promptName = "default";

}


