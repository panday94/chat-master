package com.master.chat.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.master.chat.common.enums.StatusEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_login_log")
public class LoginLog implements Serializable {

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
     * token过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 用户id
     */
    private Long sysUserId;

    /**
     * 会话标识
     */
    private String sessionId;

    /**
     * 用户名
     */
    private String username;

    /**
     * ip地址
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
     * 登录信息
     */
    private String msg;

    /**
     * 身份标识
     */
    private String authorization;

    /**
     * 浏览器类型
     */
    private String userAgent;

    /**
     * 登录状态 0 失败 1 成功
     */
    private Integer status;

    /**
     * 是否启用 0 禁用 1 启用
     */
    private Integer enabled;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

    public Boolean isLoginSuccess() {
        return StatusEnum.ENABLED.getValue().equals(this.status);
    }

}
