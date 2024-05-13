package com.master.chat.common.config;

import lombok.Data;

/**
 * 系统预警通知企业微信配置
 *
 * @author: Yang
 * @date: 2023/5/28
 * @version: 1.0.0

 */
@Data
public class SystemWarningConfig {

    /**
     * 企业id
     */
    private String coprid;

    /**
     * 应用id
     */
    private int agentid;

    /**
     * 应用密钥
     */
    private String coprsecret;

    /**
     * 应用包名
     */
    private String packages;

}
