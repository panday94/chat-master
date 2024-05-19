package com.master.chat.sys.pojo.dto.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信信息
 *
 * @author: Yang
 * @date: 2023/5/6
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class WxInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小程序id
     */
    private String maAppId;

    /**
     * 小程序密钥
     */
    private String maSecret;

    /**
     * 是否开启小程序支付
     */
    private Integer maPay;

    /**
     * 公众号登录
     */
    private Integer mpLogin;

    /**
     * 是否开启公众号支付
     */
    private Integer mpPay;

    /**
     * 公众号id
     */
    private String mpAppId;

    /**
     * 公众号密钥
     */
    private String mpSecret;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * V3密钥
     */
    private String v3Secret;

    /**
     * 支付证书信息
     */
    private String cert;

}
