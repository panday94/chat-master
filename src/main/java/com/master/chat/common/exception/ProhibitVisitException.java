package com.master.chat.common.exception;

import com.master.chat.common.enums.ResponseEnum;
import lombok.Data;

/**
 * 账号认证失败异常
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 3.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ProhibitVisitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public ProhibitVisitException() {
        this.code = ResponseEnum.PROHIBIT_VISIT.getCode();
        this.msg = ResponseEnum.PROHIBIT_VISIT.getMsg();
    }

    public ProhibitVisitException(String msg) {
        super(msg);
        this.code = ResponseEnum.PROHIBIT_VISIT.getCode();
        this.msg = msg;
    }

}
