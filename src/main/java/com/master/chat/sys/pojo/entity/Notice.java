package com.master.chat.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统通知
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_notice")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公告类型（1通知 2消息）
     */
    private Integer type;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;


}
