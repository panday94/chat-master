package com.master.chat.gpt.pojo.command;

import com.master.chat.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;

/**
 *  openai token对象 Command
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class OpenkeyCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * appid
     */
    private String appId;

    /**
     * appkey
     */
    private String appKey;

    /**
     * app密钥
     */
    private String appSecret;


    /**
     * 总额度
     */
    private Long totalTokens;

    /**
     * 已用额度
     */
    private Long usedTokens;

    /**
     * 剩余额度
     */
    private Long surplusTokens;

    /**
     * 是否可用 0 禁用 1 启用
     */
    private Integer status;

    /**
     * 模型
     */
    private String model;

    /**
     * 备注
     */
    private String remark;

}
