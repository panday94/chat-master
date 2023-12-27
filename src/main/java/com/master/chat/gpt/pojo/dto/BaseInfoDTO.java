package com.master.chat.gpt.pojo.dto;

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

    private Integer contentSecurity;

    private String siteTitle;

    private Integer proxyType;

    private String proxyServer;

    private String domain;

    private String copyright;

    private String descrip;

    private List<String> keywords;

}
