package com.master.chat.sys.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户信息返回对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
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
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 用户标识
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
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 0->女;1->男
     */
    private Integer gender;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 是否管理员 0->否;1->是
     */
    private Boolean admind;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门id信息
     */
    private List<Long[]> deptIds;

    /**
     * 角色id信息
     */
    private List<Long> roleIds;

    /**
     * 岗位id信息
     */
    private List<Long> postIds;

    /**
     * 角色集合
     */
    private Set<String> roles;

    /**
     * 权限信息
     */
    private Set<String> permissions;

}
