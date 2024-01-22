package com.master.chat.sys.pojo.dto.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础信息
 *
 * @author: yang
 * @date: 2023/5/6
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class ExtraInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ossType;

    private String uploadSize;

    private String ossKeyId;

    private String ossKeySecret;

    private Integer smsType;

    private String smsKeyId;

    private String smsKeySecret;

}
