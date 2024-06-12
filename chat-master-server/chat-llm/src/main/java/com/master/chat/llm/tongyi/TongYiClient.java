package com.master.chat.llm.tongyi;

import lombok.Data;

/**
 * 通义千问client
 * 文档地址：https://help.aliyun.com/zh/dashscope/developer-reference/model-square/?disableWebsiteRedirect=true
 *
 * @author: Yang
 * @date: 2023/12/4
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class TongYiClient {

    private String appKey;

    public TongYiClient() {
    }

    public TongYiClient(String appKey) {
        this.appKey = appKey;
    }

}
