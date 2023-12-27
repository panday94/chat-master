package com.master.chat.gpt.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  文件管理对象 VO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class UploadFileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 图片路径
     */
    private String filepath;

    /**
     * 文件hash值
     */
    private String hash;

    /**
     * 存储方式
     */
    private String disk;

    /**
     * 文件类型
     */
    private Integer type;

}
