package com.master.chat.sys.pojo.dto.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 基础信息
 *
 * @author: yang
 * @date: 2023/5/6
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class BaseInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内容安全审查接口 0 不检查 1 检查
     */
    private Integer contentSecurity;

    /**
     * 站点名称
     */
    private String siteTitle;

    /**
     * 站点logo
     */
    private String siteLogo;

    /**
     * 代理方案 1 环境代理 2 方向代理 3 直接代理
     */
    private Integer proxyType;

    /**
     * 代理地址
     */
    private String proxyServer;

    /**
     * 域名
     */
    private String domain;

    /**
     * 站点版权
     */
    private String copyright;

    /**
     * 站点描述
     */
    private String descrip;

    /**
     * 关键词
     */
    private List<String> keywords;

}
