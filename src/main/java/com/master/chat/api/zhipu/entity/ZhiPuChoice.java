package com.master.chat.api.zhipu.entity;

import com.google.gson.annotations.SerializedName;
import com.zhipu.oapi.service.v4.model.Usage;
import lombok.Data;

import java.io.Serializable;

/**
 * 智谱清言 返回
 *
 * @author: Yang
 * @date: 2023/12/27
 * @version: 1.0.0
 * Copyright Ⓒ 2023 MasterComputer Corporation Limited All rights reserved.
 */
@Data
public class ZhiPuChoice implements Serializable {

    @SerializedName("task_status")
    private String taskStatus;
    @SerializedName("usage")
    private Usage usage;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("request_id")
    private String requestId;

}
