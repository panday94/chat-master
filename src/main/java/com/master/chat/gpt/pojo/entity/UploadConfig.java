package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

;

/**
 *  缩略图配置对象 gpt_upload_config
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_upload_config")
public class UploadConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
