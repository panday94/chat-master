package com.master.chat.gpt.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  兑换码对象 DTO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class RedemptionDTO implements Serializable {

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
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 兑换码
     */
    private String code;

    /**
     * 可兑次数
     */
    private Long num;

    /**
     * 兑换人
     */
    private Long userId;

    /**
     * 兑换时间
     */
    private Integer recieveTime;

    /**
     * 状态
     */
    private Integer status;

}
