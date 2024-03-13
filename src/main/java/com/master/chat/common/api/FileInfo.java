package com.master.chat.common.api;

import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.utils.file.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件信息
 *
 * @author: Yang
 * @date: 2020/11/18
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原文件名称
     */
    private String fileName;

    /**
     * 文件访问路径
     */
    private String fileUrl;

    /**
     * 文件路径名称
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private String size;

    public FileInfo(String fileName, String fileUrl, long size) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.filePath = fileUrl;
        this.type = fileName.substring(fileName.lastIndexOf(StringPoolConstant.DOT) + 1);
        this.size = FileUtil.getFileSize(size);
    }

    public FileInfo(String fileName, String fileUrl, String filePath, long size) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.filePath = filePath;
        this.type = fileName.substring(fileName.lastIndexOf(StringPoolConstant.DOT) + 1);
        this.size = FileUtil.getFileSize(size);
    }

}
