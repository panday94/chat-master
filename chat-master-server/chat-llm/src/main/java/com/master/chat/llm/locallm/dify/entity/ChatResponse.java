package com.master.chat.llm.locallm.dify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;

@Data
@ToString
public class ChatResponse implements Serializable {

    /**
     * 返回类型
     */
    private String event;

    /**
     * 创建时间戳
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 任务ID
     */
    @JsonProperty("task_id")
    private String taskId;

    /**
     *  消息唯一 ID
     */
    @JsonProperty("message_id")
    private String messageId;

    /**
     * 会话 ID
     */
    @JsonProperty("conversation_id")
    private String conversationId;

    /**
     * 返回文本块内容
     */
    private String answer;

    /**
     * event: agent_thought
     * agent_thought在消息中的位置
     */
    private Integer position;

    /**
     * event: agent_thought
     * agent的思考内容
     */
    private String thought;

    /**
     * event: agent_thought
     * 工具调用的返回结果
     */
    private String observation;

    /**
     * event: agent_thought
     * 使用的工具列表
     */
    private String tool;

    /**
     * event: agent_thought
     * 工具的输入
     */
    @JsonProperty("tool_input")
    private String toolInput;

    /**
     * event: agent_thought
     * 当前 agent_thought 关联的文件
     */
    @JsonProperty("message_files")
    private String[] messageFiles;

    /**
     * event: message_file
     * 文件访问地址
     */
    private String url;

    /**
     * event: tts_message
     * 语音合成之后的音频块使用 Base64 编码
     */
    private String audio;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

}
