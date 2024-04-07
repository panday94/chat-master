package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审核日志返回信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class AuditLogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 审核状态 0->审核拒绝;1->审核通过
     */
    private Integer status;

    /**
     * 审核类型
     */
    private Integer type;

    /**
     * 审核内容
     */
    private String content;

    /**
     * 图片
     */
    private String pic;

    /**
     * 备注
     */
    private String remark;

}
