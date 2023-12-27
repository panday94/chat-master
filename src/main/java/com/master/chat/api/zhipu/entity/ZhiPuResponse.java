package com.master.chat.api.zhipu.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 智谱清言 返回
 *
 * @author: yang
 * @date: 2023/12/27
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
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
