package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.framework.validator.group.AddGroup;
import com.master.chat.framework.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 资源
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ResourceCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "缺少资源id", groups = UpdateGroup.class)
    @Min(value = 1, message = "资源id必须大于1", groups = UpdateGroup.class)
    private Long id;

    /**
     * 资源名称
     */
    @NotNull(message = "缺少资源名称", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 标题名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由路径
     */
    @NotNull(message = "缺少路由路径", groups = {AddGroup.class, UpdateGroup.class})
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
    @NotNull(message = "缺少上级菜单id", groups = {AddGroup.class, UpdateGroup.class})
    private Long parentId;

    /**
     * 是否重定向 0：否 1: 是
     */
    private Integer redirect;

    /**
     * 类型：1：目录 2: 菜单 3：操作资源
     */
    @NotNull(message = "缺少资源类型", groups = {AddGroup.class, UpdateGroup.class})
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

}
