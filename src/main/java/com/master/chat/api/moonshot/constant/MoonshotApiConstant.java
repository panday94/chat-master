package com.master.chat.api.moonshot.constant;

/**
 * 月之暗面 接口常量
 *
 * @author: Yang
 * @date: 2024/3/26
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface MoonshotApiConstant {

    String BASE_COMPLETION_URL = "https://api.moonshot.cn/v1";

    /**
     * 对话
     */
    String CHAT_COMPLETION_URL = BASE_COMPLETION_URL + "/chat/completions";

    /**
     * 计算token
     */
    String ESTIMATE_TOKEN_COUNT_URL = BASE_COMPLETION_URL + "/tokenizers/estimate-token-count";

    /**
     * 获取模型列表
     */
    String CHAT_LIST_MODELS_URL = BASE_COMPLETION_URL + "/models";

    /**
     * 文件列表
     */
    String UPLOAD_FILES_URL = BASE_COMPLETION_URL + "/files";
}