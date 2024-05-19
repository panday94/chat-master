package com.master.chat.framework.properties;

import com.master.chat.common.api.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 获取ali配置文件
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Component
@ConfigurationProperties(prefix = "ali", ignoreInvalidFields = true)
@Setter
@Getter
@ToString
@Order(1)
public class AliProperties {

    /**
     * OSS配置
     */
    private Key oss;

    /**
     * Sms配置
     */
    private Key sms;

    /**
     * DingTalk配置
     */
    private Key dingTalk;

}
