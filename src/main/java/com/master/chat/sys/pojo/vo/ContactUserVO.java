package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 好友列表信息
 *
 * @author: wangfu
 * @date: @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ContactUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名/账号
     */
    private String username;

    /**
     * 用户标识
     */
    private String uid;

    /**
     * 邮箱
     */
    private String email;

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
     * 性别 0->女;1->男
     */
    private Integer gender;

    /**
     * 是否在线 0->不在线;1->在线
     */
    private Integer online;

    /**
     * 所属部门集合
     */
    private List<DeptVO> depts;

    /**
     * 所在岗位集合
     */
    private List<PostVO> posts;

    /**
     * 角色集合
     */
    private List<RoleVO> roles;

}
