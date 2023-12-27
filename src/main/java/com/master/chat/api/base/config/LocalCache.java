package com.master.chat.api.base.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;

/**
 * 缓存
 *
 * @author: yang
 * @date: 2023/9/7
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
 */
public class LocalCache {

    /**
     * 缓存时长
     */
    public static final long TIMEOUT = 5 * DateUnit.MINUTE.getMillis();


    /**
     * 清理间隔
     */
    private static final long CLEAN_TIMEOUT = 5 * DateUnit.MINUTE.getMillis();

    /**
     * 缓存对象
     */
    public static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(TIMEOUT);

    static {
        //启动定时任务
        CACHE.schedulePrune(CLEAN_TIMEOUT);
    }

}
