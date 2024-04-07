package com.master.chat.gpt.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  缩略图配置对象 VO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class UploadConfigVO implements Serializable {

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
     * 配置名称
     */
    private String title;

    /**
     * 覆盖同名文件
     */
    private Integer uploadReplace;

    /**
     * 缩图开关
     */
    private Integer thumbStatus;

    /**
     * 缩放宽度
     */
    private String thumbWidth;

    /**
     * 缩放高度
     */
    private String thumbHeight;

    /**
     * 缩图方式
     */
    private Integer thumbType;

}
