package com.master.chat.common.converter;

import com.github.dozermapper.core.DozerConverter;
import com.master.chat.common.constant.StringPoolConstant;

import java.math.BigDecimal;

/**
 * BigDecimal数据类型转String
 *
 * @author: Yang
 * @date: 2021/1/15
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class BigDecimalToStringConverter extends DozerConverter<BigDecimal, String> {

    public BigDecimalToStringConverter() {
        super(BigDecimal.class, String.class);
    }

    @Override
    public String convertTo(BigDecimal a1, String a2) {
        return a1 == null ? StringPoolConstant.ZERO : a1.toPlainString();
    }

    @Override
    public BigDecimal convertFrom(String a1, BigDecimal a2) {

        return null;
    }


}
