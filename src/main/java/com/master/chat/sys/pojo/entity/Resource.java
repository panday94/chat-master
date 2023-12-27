package com.master.chat.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 资源
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_resource")
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 标题名称
     */
    private String title;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 是否重定向 0：否 1: 是
     */
    private Integer redirect;

    /**
     * 类型：1：目录 2: 菜单 3：操作资源
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 是否隐藏 0->不隐藏;1->隐藏
     */
    private Integer hidden;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;


}
