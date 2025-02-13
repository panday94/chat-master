package com.master.chat.llm.spark.enums;

import lombok.Getter;

/**
 * 讯飞星火 API接口
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum ModelEnum {

    /**
     * 星火大模型API当前有Lite、Pro、Pro-128K、Max、Max-32K和4.0 Ultra六个版本 和 科技文献大模型（kjwx），各版本独立计量tokens
     * https://www.xfyun.cn/doc/spark/Web.html#_1-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E
     */
    Lite("v1.1", "https://spark-api.xf-yun.com/v1.1/chat", "lite"),

    PRO("v3.1", "https://spark-api.xf-yun.com/v3.1/chat", "generalv3"),
    PRO_128K("v3.1-128k", "https://spark-api.xf-yun.com/chat/pro-128k", "pro-128k"),
    MAX("v3.5", "https://spark-api.xf-yun.com/v3.5/chat", "generalv3.5"),
    MAX_32K("v3.5-32k", "https://spark-api.xf-yun.com/chat/max-32k", "max-32k"),
    ULTRA("v4.0", "https://spark-api.xf-yun.com/v4.0/chat", "4.0Ultra"),
    ;

    private final String version;

    private final String url;

    private final String domain;

    ModelEnum(String version, String url, String domain) {
        this.version = version;
        this.url = url;
        this.domain = domain;
    }

    public static ModelEnum getEnum(String version) {
        for (ModelEnum value : ModelEnum.values()) {
            if (version.equals(value.version)) {
                return value;
            }
        }
        return null;
    }

}
