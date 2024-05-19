package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 *  AI助理功能对象 gpt_assistant
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_assistant")
public class Assistant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色图标
     */
    private String icon;

    /**
     * 角色名称
     */
    private String title;

    /**
     * 标签
     */
    private String tag;

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

    /**
     * 主模型
     */
    private Integer mainModel;

    /**
     * 分类id
     */
    private Long typeId;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
