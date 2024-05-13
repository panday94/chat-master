package com.master.chat.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统参数相关key
 *
 * @author: Yang
 * @date: 2019/8/16
 * @version: 1.0.0

 */
@Component
public class ApplicationConfig {

    /**
     * 环境
     */
    public static String active;

    @Value("${spring.profiles.active}")
    public void setActive(String active) {
        ApplicationConfig.active = active;
    }

}
