package com.master.chat.client.service;

/**
 * 配置服务
 *
 * @author: Yang
 * @date: 2024/5/29
 * @version: 1.0.0
 * Copyright Ⓒ 2024 Master Computer Corporation Limited All rights reserved.
 */
public interface BaseConfigService {

    /**
     * 根据配置名称获取配置信息
     *
     * @param name 配置名称
     * @return
     */
    <T> T getBaseConfigByName(String name, Class<T> tClass);

}
