package com.master.chat.common.converter;

import com.github.dozermapper.core.DozerConverter;

/**
 * 转为数值类型处理
 *
 * @author: Yang
 * @date: 2023/1/15
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public class ToNumberConverter extends DozerConverter<Object, Object> {

    public ToNumberConverter() {
        super(Object.class, Object.class);
    }

    @Override
    public Object convertTo(Object a1, Object a2) {
        return a1 instanceof Number ? (Number) a1 : a1;
    }

    @Override
    public Object convertFrom(Object a1, Object a2) {
        return a1 instanceof Number ? (Number) a1 : a1;
    }


}
