package com.master.chat.api.wenxin.constant;

/**
 * 文心一言模型
 * 文档地址：https://cloud.baidu.com/doc/WENXINWORKSHOP/s/Nlks5zkzu
 *
 * @author: Yang
 * @date: 2023/9/7
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public enum ModelE {
    /**
     * 模型版本 接口地址
     */
    ERNIE_Bot("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions", "ERNIE_Bot"),
    ERNIE_Bot_8k("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie_bot_8k", "ERNIE_Bot-8k"),

    ERNIE_Bot_turbo("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant", "ERNIE_Bot_turbo"),

    ERNIE_Bot_4("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro", "ERNIE_Bot 4.0"),

    // 绘画模型
    STABLE_DIFFUSION_XL("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/text2image/sd_xl", "Stable-Diffusion-XL"),

    ;
    private final String apiHost;
    private final String label;

    ModelE(String code, String label) {
        this.apiHost = code;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getApiHost() {
        return apiHost;
    }

    public static ModelE getEnum(String label) {
        for (ModelE value : ModelE.values()) {
            if (label.equals(value.label)) {
                return value;
            }
        }
        return null;
    }

}
