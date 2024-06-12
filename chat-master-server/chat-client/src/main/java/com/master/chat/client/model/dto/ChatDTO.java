package com.master.chat.client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 聊天摘要对象 DTO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模型
     */
    private String model;

    /**
     * stream
     */
    private Boolean stream;

    /**
     * 模版
     */
    private Double temperature;

    /**
     * 内容长度
     */
    private Integer max_tokens;

    /**
     * 消息内容
     */
    private List<ChatMessageDTO> messages;

}
