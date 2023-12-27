package com.master.chat.api.zhipu.entity;

import com.google.gson.annotations.SerializedName;
import com.zhipu.oapi.service.v3.Usage;
import lombok.Data;

import java.io.Serializable;

/**
 * 智谱清言 返回
 *
 * @author: yang
 * @date: 2023/12/27
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
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
