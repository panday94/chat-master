package com.master.chat.llm.doubao.enums;

import lombok.Getter;

/**
 * 豆包 API接口
 *
 * @author: Yang
 * @date: 2025/2/16
 * @version: 1.2.4
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum ModelEnum {

    /**
     * 豆包 API
     * 1、开通模型服务
     * 2、创建在线推理
     * https://www.volcengine.com/docs/82379/1263272
     */
    LITE(""),

    PRO(""),

    ;

    private final String model;

    ModelEnum(String model) {
        this.model = model;
    }

}
