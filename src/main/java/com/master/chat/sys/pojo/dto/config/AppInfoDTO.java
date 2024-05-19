package com.master.chat.sys.pojo.dto.config;

import lombok.Data;

import java.io.Serializable;

/**
 * APP信息
 *
 * @author: Yang
 * @date: 2023/5/6
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class AppInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否无限制访问GPT
     */
    private Integer isGPTLimit;

    /**
     * 是否开启分享
     */
    private Integer isShare;

    /**
     * 是否开启短信
     */
    private Integer isSms;

    /**
     * 首页公共
     */
    private String homeNotice;

    /**
     * 分享赠送次数
     */
    private String shareRewardNum;

    /**
     * 免费体验次数
     */
    private String freeNum;

    /**
     * H5地址
     */
    private String h5Url;

}
