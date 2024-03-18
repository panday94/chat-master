package com.master.chat.api.xfyun.constant;

import lombok.Getter;

/**
 * 讯飞星火 API接口
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum SparkApiVersion {

    /**
     * 1.5版本
     */
    V1_5("v1.1", "https://spark-api.xf-yun.com/v1.1/chat", "general"),

    /**
     * 2.0版本
     */
    V2_0("v2.1", "https://spark-api.xf-yun.com/v2.1/chat", "generalv2"),
    V3_0("v3.1", "https://spark-api.xf-yun.com/v3.1/chat", "generalv3"),
    V3_5("v3.5", "https://spark-api.xf-yun.com/v3.5/chat", "generalv3.5"),
    ;

    private final String version;

    private final String url;

    private final String domain;

    SparkApiVersion(String version, String url, String domain) {
        this.version = version;
        this.url = url;
        this.domain = domain;
    }

    public static SparkApiVersion getEnum(String version) {
        for (SparkApiVersion value : SparkApiVersion.values()) {
            if (version.equals(value.version)) {
                return value;
            }
        }
        return null;
    }

}
