package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 会员用户对象 gpt_user
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * uuid
     */
    private String uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * openid
     */
    private String openid;

    /**
     * unionid
     */
    private String unionid;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 是否开启上下文
     */
    private Boolean context;

    /**
     * 调用次数
     */
    private Long num;

    /**
     * 邀请人
     */
    private Long shareId;

    /**
     * 用户类型 1 微信小程序 2 公众号 3 手机号
     */
    private Integer type;

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
