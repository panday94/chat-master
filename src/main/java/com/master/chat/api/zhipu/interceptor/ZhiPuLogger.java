package com.master.chat.api.zhipu.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 智谱清言logger
 *
 * @author: yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
public class ZhiPuLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        log.info("智谱清言>>>:{}", message);
    }

}
