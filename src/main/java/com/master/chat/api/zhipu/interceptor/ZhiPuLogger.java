package com.master.chat.api.zhipu.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 智谱清言logger
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
public class ZhiPuLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        log.info("智谱清言>>>:{}", message);
    }

}
