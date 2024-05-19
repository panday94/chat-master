package com.master.chat.framework.third;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 初始化微信应用配置
 *
 * @author: Yang
 * @date: 2020/12/24
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 **/
@Component
@Order(value = 1)
@Slf4j
public class InitWxServiceCacheRunner implements CommandLineRunner {

    /**
     * 初始化微信服务
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        initMaService();
    }

    /**
     * 初始化小程序服务
     */
    private void initMaService() {
        WxMaService service = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(WxAppConstant.APP_ID);
        config.setSecret(WxAppConstant.APP_SECRET);
        service.setWxMaConfig(config);
        WxMaServiceKit.setMaService(WxAppConstant.APP_ID, service);
        log.info("初始化微信小程序服务");
    }

}
