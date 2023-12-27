package com.master.chat.framework.web.entity;

import com.master.common.constant.StringPoolConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 缓存信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysCache {

    /**
     * 缓存名称
     */
    private String cacheName = StringPoolConstant.EMPTY;

    /**
     * 缓存键名
     */
    private String cacheKey = StringPoolConstant.EMPTY;

    /**
     * 缓存内容
     */
    private String cacheValue = StringPoolConstant.EMPTY;

    /**
     * 备注
     */
    private String remark = StringPoolConstant.EMPTY;


    public SysCache(String cacheName, String remark) {
        this.cacheName = cacheName;
        this.remark = remark;
    }

    public SysCache(String cacheName, String cacheKey, String cacheValue) {
        this.cacheName = StringUtils.replace(cacheName, StringPoolConstant.COLON, StringPoolConstant.EMPTY);
        this.cacheKey = StringUtils.replace(cacheKey, cacheName, StringPoolConstant.EMPTY);
        this.cacheValue = cacheValue;
    }

}
