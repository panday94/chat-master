package com.master.chat.common.config.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * APP信息
 *
 * @author: Yang
 * @date: 2023/5/6
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class AppInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否无限制访问GPT
     */
    private Integer isGPTLimit;

    /**
     * 是否开启兑换码
     */
    private Integer isRedemption;

    /**
     * 是否开启短信
     */
    private Integer isSms;

    /**
     * 是否开启分享
     */
    private Integer isShare;

    /**
     * 分享赠送次数
     */
    private Integer shareNum;

    /**
     * 免费体验次数
     */
    private Integer freeNum;

    /**
     * H5地址
     */
    private String h5Url;

    /**
     * 首页公告
     */
    private String homeNotice;

}
