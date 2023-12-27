package com.master.chat.gpt.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *  订单对象 DTO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class OrderDTO implements Serializable {

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
     * 支付成功时间
     */
    private LocalDateTime successTime;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     * 渠道交易ID
     */
    private String transactionId;

    /**
     * 下单用户
     */
    private Long userId;

    /**
     * 购买套餐
     */
    private Long combId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 支付渠道
     */
    private Integer chanel;

    /**
     * 订单状态
     */
    private Integer status;

}
