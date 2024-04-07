package com.master.chat.api.wenxin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文本生成图片内容
 *
 * @author: Yang
 * @date: 2024/1/6
 * @version: 1.1.1
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ImageData implements Serializable {

    /**
     * 固定值"image"
     */
    private String object;

    /**
     * 图片base64编码内容
     */
    @JsonProperty("b64_image")
    private String b64Image;

    /**
     * 序号
     */
    private Integer index;

}
