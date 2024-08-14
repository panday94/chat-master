package com.master.chat.llm.locallm.chatchat.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class Glm4Response implements Serializable {


    private String model;

    private String id;
    private String object;
    private String finish_reason;


    /**
     * 文档出处
     */
    private List<JSONObject> choices;
    private Integer created;


}
