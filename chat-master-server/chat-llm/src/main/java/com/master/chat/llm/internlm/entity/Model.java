package com.master.chat.llm.internlm.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model {

    private String id;
    private String object;
    private Long created;
    @SerializedName("owner_by")
    private String ownedBy;

}
