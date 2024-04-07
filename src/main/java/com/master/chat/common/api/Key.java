package com.master.chat.common.api;

import lombok.Getter;
import lombok.Setter;

/**
 * 应用key信息
 *
 * @author: Yang
 * @date: 2023/7/27
 * @version: 3.7.2
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
@Setter
public class Key {

    /**
     * 密钥Key
     */
    private String key;

    /**
     * 密钥Key Secret
     */
    private String secret;

}
