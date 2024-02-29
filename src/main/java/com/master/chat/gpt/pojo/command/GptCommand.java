package com.master.chat.gpt.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.api.Query;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 对话消息对象 Command
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class GptCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 聊天编号
     */
    @NotBlank(message = "缺少对话标识")
    private String chatNumber;

    /**
     * 对话id
     */
    private String conversationId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色模型id
     */
    private Long assistantId;

    /**
     * 系统提示
     */
    private String systemPrompt;

    /**
     * 提示
     */
    @NotBlank(message = "缺少提示词")
    private String prompt;

    /**
     * 使用模型
     */
    @NotBlank(message = "缺少模型信息")
    private String model;

    /**
     * 使用模型版本
     */
    private String modelVersion;

    /**
     * 消耗token
     */
    private Long tokens;

    /**
     * 是否api请求
     */
    private Boolean api;

    /**
     * 额外参数 如智谱清言 角色大模型参数{meta}
     */
    private Query query;

}
