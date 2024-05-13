package com.master.chat.gpt.enums;

import lombok.Getter;

/**
 * 审核类型枚举类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0

 */
@Getter
public enum ChatStatusEnum {

    /**
     *  回复状态 1 回复中 2正常 3 失败
     */
    REPLY(1, "回复中"),

    SUCCESS(2, "成功"),

    ERROR(3, "失败");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 标签
     */
    private final String label;

    ChatStatusEnum(final Integer value, final String label) {
        this.label = label;
        this.value = value;
    }

}
