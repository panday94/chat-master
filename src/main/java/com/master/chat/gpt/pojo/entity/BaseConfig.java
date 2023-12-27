package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 *  基础配置对象 gpt_base_config
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
@TableName("gpt_base_config")
public class BaseConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置键值
     */
    private String name;

    /**
     * 配置内容
     */
    private String data;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
