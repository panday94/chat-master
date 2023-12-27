package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资源信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ResourceVO implements Serializable {

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
     * 是否选中
     */
    private Boolean checked;

    /**
     * 子菜单
     */
    public List<ResourceVO> children;

}
