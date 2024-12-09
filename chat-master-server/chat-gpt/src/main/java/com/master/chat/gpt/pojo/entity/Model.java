package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 *  大模型信息对象 gpt_model
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
     * 本地模型类型：1、Langchian；2、ollama；3、Giteeai 4、Coze
     */
    private Integer localModelType;

    /**
     * 模型接口地址
     */
    private String modelUrl;

    /**
     * 知识库名称
     */
    private String knowledge;

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
