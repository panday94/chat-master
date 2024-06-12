package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统通知
 *
 * @author: Yang
 * @date: 2023/01/31
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class NoticeVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 公告类型（1通知 2公告）
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
}
