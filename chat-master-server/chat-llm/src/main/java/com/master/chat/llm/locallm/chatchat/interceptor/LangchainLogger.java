package com.master.chat.llm.locallm.chatchat.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;

@Slf4j
public class LangchainLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        log.info("Langchain>>>:{}", message);
    }

}
