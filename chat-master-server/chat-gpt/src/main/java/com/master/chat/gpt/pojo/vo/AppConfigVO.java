package com.master.chat.gpt.pojo.vo;

import com.master.chat.common.config.dto.AppInfoDTO;
import com.master.chat.common.config.dto.BaseInfoDTO;
import com.master.chat.common.config.dto.ExtraInfoDTO;
import com.master.chat.common.config.dto.WxInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * app配置信息
 *
 * @author: Yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
    private BaseInfoDTO baseInfo;

    /**
     * 额外配置信息
     */
    private ExtraInfoDTO extraInfo;

    /**
     * app信息
     */
    private AppInfoDTO appInfo;

    /**
     * 微信信息
     */
    private WxInfoDTO wxInfo;

    /**
     * 主要模型
     */
    private AssistantVO mainAssistant;

    /**
     * 所有模型
     */
    private List<AssistantVO> assistants;

}
