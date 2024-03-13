package com.master.chat.framework.third;

import cn.binarywang.wx.miniapp.api.WxMaService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信小程序配置缓存
 *
 * @author: Yang
 * @date: 2021/1/19
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
public class WxMaServiceKit {

    private static final Map<String, WxMaService> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * 获取当前线程中的 WxMaService
     *
     * @param clientId 客户端标识 区分不同配置文件
     * @return {@link WxMaService}
     */
    public static WxMaService getMaService(String clientId) {
        return getService(clientId);
    }

    /**
     * 通过 clientId 获取 WxMaService
     *
     * @param clientId 客户端标识
     * @return {@link WxMaService}
     */
    private static WxMaService getService(String clientId) {
        WxMaService service = SERVICE_MAP.get(clientId);
        if (service == null) {
            log.error("需事先调用 WxMaServiceKit.setMaService(clientId, service) 将 appId对应的 WxMaService 对象存入，才可以使用 WxMaServiceKit.getMaService() 的系列方法");
        }
        return service;
    }

    /**
     * 向当前线程中设置支付配置信息
     *
     * @param clientId 客户端标识
     * @param service  配置信息
     */
    public static void setMaService(String clientId, WxMaService service) {
        putService(clientId, service);
    }

    /**
     * <p>向缓存中设置 WxMaService </p>
     * <p>每个 appId 只需添加一次，相同 appId 将被覆盖</p>
     *
     * @param clientId 客户端标识
     * @param service  配置信息
     */
    private static void putService(String clientId, WxMaService service) {
        SERVICE_MAP.put(clientId, service);
    }


    /**
     * 通过 clientId 移除配置
     *
     * @param clientId 客户端标识
     */
    public static void removeMaService(String clientId) {
        removeService(clientId);
    }


    /**
     * 通过 appId 移除配置
     *
     * @param clientId 客户端标识
     */
    private static void removeService(String clientId) {
        SERVICE_MAP.remove(clientId);
    }

}
