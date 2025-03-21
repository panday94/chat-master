package com.master.chat.llm.locallm.dify.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.master.chat.llm.locallm.base.entity.BaseChatCompletion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Slf4j
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChatCompletion extends BaseChatCompletion {

    /**
     * 用户输入/提问内容
     */
    private String query;

    /**
     * 是否流式输出
     * streaming 流式模式（推荐）
     * blocking 阻塞模式，等待执行完毕后返回结果
     */
    @JsonProperty("response_mode")
    private String responseMode = "streaming";

    /**
     * 允许传入 App 定义的各变量值。 inputs 参数包含了多组键值对（Key/Value pairs），每组的键对应一个特定变量，每组的值则是该变量的具体值。 默认 {}
     */
    @JsonProperty("inputs")
    private Map<String, String> inputs;

    /**
     * 用户标识
     */
    private String user;

    /**
     * 会话ID
     */
    @JsonProperty("conversation_id")
    private String conversationId;

    /**
     * 上传的文件
     */
    @JsonProperty("files")
    private List<FileItem> files;

    /**
     * 自动生成标题
     */
    @JsonProperty("auto_generate_name")
    private Boolean autoGenerateName;

}


