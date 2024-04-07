package com.master.chat.common.exception;

import com.master.chat.common.enums.ResponseEnum;
import lombok.Data;

/**
 * 权限不足异常
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 3.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class PermissionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public PermissionException() {
        this.code = ResponseEnum.PERMISSION_DENIED.getCode();
        this.msg = ResponseEnum.PERMISSION_DENIED.getMsg();
    }

    public PermissionException(String msg) {
        super(msg);
        this.code = ResponseEnum.PERMISSION_DENIED.getCode();
        this.msg = msg;
    }

}
