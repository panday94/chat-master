package com.master.chat.gpt.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  AI助理功能对象 VO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class AssistantVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
     * 助手分类id
     */
    private Long typeId;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 角色名称
     */
    private String title;

    /**
     * 角色图标
     */
    private String icon;

    /**
     * 标签
     */
    private String tag;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 主模型
     */
    private Integer mainModel;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * AI打招呼
     */
    private String firstMessage;

}
