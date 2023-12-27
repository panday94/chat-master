package com.master.chat.gpt.pojo.command;

import com.master.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;

/**
 *  文件管理对象 Command
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class UploadFileCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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
