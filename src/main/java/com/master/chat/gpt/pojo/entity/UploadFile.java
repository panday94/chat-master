package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

;

/**
 *  文件管理对象 gpt_file
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_upload_file")
public class UploadFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
