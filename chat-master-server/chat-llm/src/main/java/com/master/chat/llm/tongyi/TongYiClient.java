package com.master.chat.llm.tongyi;

import cn.hutool.core.util.StrUtil;
import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.llm.base.key.KeyUpdater;
import com.master.chat.llm.chatglm.ChatGLMClient;
import lombok.Data;

/**
 * 通义千问client
 * 文档地址：https://help.aliyun.com/zh/dashscope/developer-reference/model-square/?disableWebsiteRedirect=true
 *
 * @author: Yang
 * @date: 2023/12/4
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
public class TongYiClient implements KeyUpdater {

    private String apiKey;

    public TongYiClient() {
    }

    public TongYiClient(Builder builder) {
        if (StrUtil.isBlank(builder.apiKey)) {
            throw new ValidateException("构造错误: apiKey不能为空");
        }
        this.apiKey = builder.apiKey;
    }


    @Override
    public String supportModel() {
        return ChatModelEnum.TONGYI.getValue();
    }

    @Override
    public void updateKey(KeyModel keyModel) {
        this.setApiKey(keyModel.getAppKey());
    }

    /**
     * 构造
     *
     * @return
     */
    public static TongYiClient.Builder builder() {
        return new TongYiClient.Builder();
    }

    public static final class Builder {
        private String apiKey;

        public Builder() {
        }

        public TongYiClient.Builder apiKey(String val) {
            apiKey = val;
            return this;
        }

        public TongYiClient build() {
            return new TongYiClient(this);
        }
    }

}
