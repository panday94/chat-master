package com.master.chat.gpt.pojo.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * app配置信息
 *
 * @author: yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 基础配置信息
     */
    private JSONObject baseInfo;

    /**
     * 额外配置信息
     */
    private JSONObject extraInfo;

    /**
     * app信息
     */
    private JSONObject appInfo;

    /**
     * 微信信息
     */
    private JSONObject wxInfo;

    /**
     * 主要模型
     */
    private AssistantVO mainAssistant;

    /**
     * 所有模型
     */
    private List<AssistantVO> assistants;

}
