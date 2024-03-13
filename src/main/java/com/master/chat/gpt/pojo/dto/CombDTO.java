package com.master.chat.gpt.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *  会员套餐对象 DTO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class CombDTO implements Serializable {

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
     * 套餐名称
     */
    private String title;

    /**
     * 套餐类型
     */
    private Integer type;

    /**
     * 包含次数
     */
    private Long num;

    /**
     * 天数
     */
    private Long days;

    /**
     * 原价
     */
    private BigDecimal originPrice;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 状态
     */
    private Integer status;

}
