package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

;

/**
 *  会员套餐对象 gpt_comb
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
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_comb")
public class Comb extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 套餐名称
     */
    private String title;

    /**
     * 套餐类型 1 次数 2 天数
     */
    private Integer type;

    /**
     * 包含次数/天数
     */
    private Long num;

    /**
     * 原价
     */
    private BigDecimal originPrice;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 状态 0 禁用 1 启用
     */
    private Integer status;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
