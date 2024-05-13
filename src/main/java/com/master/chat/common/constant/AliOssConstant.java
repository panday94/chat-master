package com.master.chat.common.constant;

/**
 * 阿里云oss格式
 *
 * @author: Yang
 * @date: 2023/11/22
 * @version: 1.2.0

 */
public interface AliOssConstant {

    /**
     * 查看指定大小的云图片
     * logo图标
     */
    String STYLE_LOGO = "?x-oss-process=style/logo";
    /**
     * 查看指定大小的云图片
     * 作滚动图
     */
    String STYLE_BIGLOGO = "?x-oss-process=style/biglogo";

    /**
     * 查看指定大小的云图片
     * 类别图
     */
    String STYLE_CLASS = "?x-oss-process=style/class";

}
