package com.master.chat.api.base.enums;

import lombok.Getter;

/**
 * 聊天大模型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum ChatModelEnum {

    /**
     * CHAT_GPT
     */
    CHAT_GPT("CHAT_GPT", "CHAT_GPT"),

    WENXIN("WENXIN", "文心一言"),

    ZHIPU("ZHIPU", "智谱清言"),

    QIANWEN("QIANWEN", "通义千问"),

    SPARK("SPARK", "讯飞星火"),

    MOONSHOT("Moonshot", "月之暗面"),

    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 标签
     */
    private final String label;

    ChatModelEnum(final String value, final String label) {
        this.label = label;
        this.value = value;
    }

    public static ChatModelEnum getEnum(String value) {
        for (ChatModelEnum chatModelEnum : ChatModelEnum.values()) {
            if (value.equals(chatModelEnum.value)) {
                return chatModelEnum;
            }
        }
        return null;
    }

}
