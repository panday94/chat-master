package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 数字枚举
 *
 * @author: Yang
 * @date: 2020/10/21
 * @version: 1.0.0

 */
@Getter
public enum LongEnum {

    /**
     * 数值
     */
    MINUS_ONE(-1L),

    ZERO(0L),

    ONE(1L),

    TWO(2L),

    THREE(3L),

    FOUR(4L),

    FIVE(5L),

    SIX(6L),

    SEVEN(7L),

    EIGHT(8L),

    NINE(9L),

    TEN(10L),

    ELEVEN(11L),

    TWELVE(12L),

    TTHIRTEEN(13L),

    FOURTEEN(14L),

    FIFTEEN(15L),

    SIXTEEN(16L),

    SEVENTEEN(17L),

    EIGHTEEN(18L),

    NINETEEN(19L),

    TWENTY(20L);

    private final Long value;

    LongEnum(final Long value) {
        this.value = value;
    }

}
