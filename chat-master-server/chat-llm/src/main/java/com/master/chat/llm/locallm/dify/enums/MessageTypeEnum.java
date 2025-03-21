package com.master.chat.llm.locallm.dify.enums;

import lombok.Getter;

/**
 * 消息类型
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Getter
public enum MessageTypeEnum {

    /**
     * 消息内容类型
     */
    MESSAGE("message", "LLM 返回文本块事件"),
    AGENT_MESSAGE("agent_message", "Agent模式下返回文本块事件"),
    AGENT_THOUGHT("agent_thought", "Agent模式下有关Agent思考步骤的相关内容，涉及到工具调用（仅Agent模式下使用）"),
    MESSAGE_FILE("message_file", "文件事件，表示有新文件需要展示"),
    MESSAGE_END("message_end", "消息结束事件"),
    TTS_MESSAGE("tts_message", "TTS 音频流事件，即：语音合成输出"),
    TTS_MESSAGE_END("tts_message_end", "TTS 音频流结束事件"),
    MESSAGE_REPLACE("message_replace", "消息内容替换事件"),
    ERROR("error", "流式输出过程中出现的异常"),

    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 标签
     */
    private final String label;

    MessageTypeEnum(final String value, final String label) {
        this.value = value;
        this.label = label;
    }

}
