package com.master.chat.api.security;

import lombok.Builder;
import lombok.Data;

/**
 * 返回token信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@Builder
public class Oauth2Token {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 有效时间(秒)
     */
    private Long expiresIn;

}
