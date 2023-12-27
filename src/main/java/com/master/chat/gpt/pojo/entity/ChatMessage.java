package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

;

/**
 * 对话消息对象 gpt_chat_message
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("gpt_chat_message")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 聊天记录id
     */
    private Long chatId;

    /**
     * 关联消息id
     */
    private String parentMessageId;

    /**
     * 第三方消息id
     */
    private String messageId;

    /**
     * 使用模型
     */
    private String model;

    /**
     * 使用模型版本
     */
    private String modelVersion;

    /**
     * 角色模型
     */
    private String role;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 结束原因
     */
    private String finishReason;

    /**
     * 状态 1 回复中 2正常 3 失败
     */
    private Integer status;

    /**
     * 调用token
     */
    private String appKey;

    /**
     * 使用额度
     */
    private Long usedTokens;

    /**
     * 响应全文
     */
    private String response;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
