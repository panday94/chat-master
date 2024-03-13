package com.master.chat.gpt.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天摘要对象 VO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatVO implements Serializable {

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
     * 聊天编号
     */
    private String chatNumber;

    /**
     * 角色id
     */
    private Long assistantId;

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 会员id
     */
    private Long userId;

    /**
     * 会员昵称
     */
    private String userName;

    /**
     * 聊天摘要
     */
    private String title;

}
