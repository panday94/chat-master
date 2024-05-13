package com.master.chat.framework.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 获取url配置文件
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0

 */
@Component
@ConfigurationProperties(prefix = "url")
@Setter
@Getter
@ToString
public class UrlProperties {

    /**
     * 当前环境域名
     */
    private String base;

}
