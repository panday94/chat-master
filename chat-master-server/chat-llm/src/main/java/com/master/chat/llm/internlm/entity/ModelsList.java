package com.master.chat.llm.internlm.entity;

import lombok.Data;

import java.util.List;

/**
 * 模型列表
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ModelsList {

    /**
     * 模型数据
     */
    private List<Model> data;

}
