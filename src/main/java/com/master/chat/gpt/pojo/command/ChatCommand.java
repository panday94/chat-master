package com.master.chat.gpt.pojo.command;

import com.master.chat.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;

/**
 *  聊天摘要对象 Command
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ChatCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 聊天编号
     */
    private String chatNumber;

    /**
     * 角色id
     */
    private Long assistantId;

    /**
     * 会员id
     */
    private Long userId;

    /**
     * 聊天摘要
     */
    private String prompt;

}
