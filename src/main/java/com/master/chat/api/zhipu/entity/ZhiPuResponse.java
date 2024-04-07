package com.master.chat.api.zhipu.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 智谱清言 返回
 *
 * @author: Yang
 * @date: 2023/12/27
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@ToString
public class ZhiPuResponse implements Serializable {

    /**
     * 内容
     */
    private String data;

    /**
     * 内容
     */
    private String meta;

    /**
     * 返回内容 格式化处理
     */
    private ZhiPuChoice choice;

}
