package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 路由配置信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Master Computer Corporation Limited All rights reserved.
 */
@Data
public class RouterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路由名字
     */
    private Object name;

    /**
     * 路由地址
     */
    private Object path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private Object hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private Object redirect;

    /**
     * 组件地址
     */
    private Object component;

    /**
     * 路由参数：如 {"id": 1, "name": "ry"}
     */
    private Object query;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Object alwaysShow;

    /**
     * 其他元素
     */
    private MetaVO meta;

    /**
     * 子路由
     */
    private List<RouterVO> children;

}
