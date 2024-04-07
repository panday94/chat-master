package com.master.chat.api.xfyun.listener;

import com.master.chat.api.xfyun.entity.SparkSyncChatResponse;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.entity.response.SparkResponse;
import com.master.chat.api.xfyun.entity.response.SparkResponseUsage;
import okhttp3.Response;
import okhttp3.WebSocket;

import javax.validation.constraints.NotNull;

/**
 * 讯飞星火同步回答监听
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public class SparkSyncChatListener extends SparkBaseListener {

    private final StringBuilder stringBuilder = new StringBuilder();

    private final SparkSyncChatResponse sparkSyncChatResponse;

    public SparkSyncChatListener(SparkSyncChatResponse sparkSyncChatResponse) {
        this.sparkSyncChatResponse = sparkSyncChatResponse;
    }

    @Override
    public void onMessage(String content, SparkResponseUsage usage, Integer status, SparkRequest sparkRequest, SparkResponse sparkResponse, WebSocket webSocket) {
        stringBuilder.append(content);
        if (2 == status) {
            sparkSyncChatResponse.setContent(stringBuilder.toString());
            sparkSyncChatResponse.setTextUsage(usage.getText());
            sparkSyncChatResponse.setOk(true);
        }
    }

    @Override
    public void onError(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        sparkSyncChatResponse.setErrTxt(t.getMessage());
        sparkSyncChatResponse.setSuccess(false);
    }

}
