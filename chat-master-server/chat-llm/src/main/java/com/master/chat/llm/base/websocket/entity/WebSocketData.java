package com.master.chat.llm.base.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket 转换对象
 *
 * @author: Yang
 * @date: 2023/5/5
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketData {

    /**
     * 心跳码
     */
    private String functionCode;

    /**
     * 消息
     */
    private Object message;


}
