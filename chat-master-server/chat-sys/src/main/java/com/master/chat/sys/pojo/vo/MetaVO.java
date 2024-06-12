package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 路由显示信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class MetaVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private Object title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private Object icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    private Object noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    private Object link;

    public MetaVO() {
    }

    public MetaVO(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaVO(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVO(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

}
