package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

;
;

/**
 *  大模型信息对象 gpt_model
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_model")
public class Model extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 模型logo
     */
    private String icon;

    /**
     * 模型名称
     */
    private String model;

    /**
     * 模型版本
     */
    private String version;

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
