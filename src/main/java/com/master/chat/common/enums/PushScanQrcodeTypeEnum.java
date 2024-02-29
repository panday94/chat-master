package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 订个活招聘推送扫码类型
 *
 * @author：周杰
 * @date: 2023/5/6
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 **/
@Getter
public enum PushScanQrcodeTypeEnum {
    //1->绑定openId/unionId" 2->添加扫码/关注记录
    BIND_ID(1, "绑定openId/unionId"),

    SCAN_QRCODE(2, "添加扫码/关注记录");


    private Integer code;
    private String value;

    /**
     * 构造方法
     *
     * @param code
     * @param value
     */
    private PushScanQrcodeTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 普通方法
     *
     * @param code
     * @return
     */
    public static String getVlue(Integer code) {
        for (PushScanQrcodeTypeEnum c : PushScanQrcodeTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.value;
            }
        }
        return null;
    }


}
