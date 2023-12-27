package com.master.chat.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 操作人id
     */
    private Long sysUserId;

    /**
     * 操作记录id
     */
    private Long fkId;

    /**
     * 操作人
     */
    private String username;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 登录地址
     */
    private String address;

    /**
     * 域名
     */
    private String domain;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 接口名称
     */
    private String uri;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 系统模块
     */
    private String title;

    /**
     * 业务类型 {@link com.master.common.enums.BusinessTypeEnum}
     */
    private String businessType;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行结果
     */
    private String result;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
