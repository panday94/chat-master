package com.master.chat.llm.spark.entity.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 响应内容
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatSyncResponse implements Serializable {
    private static final long serialVersionUID = -6785055441385392782L;

    /**
     * 回答内容
     */
    private String content;

    /**
     * tokens统计
     */
    private Usage textUsage;

    /**
     * 内部自用字段
     */
    private boolean ok = false;

    /**
     * 成功状态
     */
    private boolean success = false;

    /**
     * 错误原因
     */
    private String errTxt;

}
