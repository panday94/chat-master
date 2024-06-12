package com.master.chat.common.converter;

import com.github.dozermapper.core.DozerConverter;

import java.math.BigDecimal;

/**
 * Double数据类型转BigDecimal
 *
 * @author: Yang
 * @date: 2023/1/15
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public class DoubleToBigDecimalConverter extends DozerConverter<Double, BigDecimal> {
    public DoubleToBigDecimalConverter() {
        super(Double.class, BigDecimal.class);
    }

    @Override
    public BigDecimal convertTo(Double a1, BigDecimal a2) {
        a1 = a1 == null ? 0.0 : a1;
        return new BigDecimal(a1);
    }

    @Override
    public Double convertFrom(BigDecimal a1, Double a2) {
        return null;
    }
}
