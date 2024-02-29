package com.master.chat.common.exception;

import com.master.chat.common.enums.ResponseEnum;
import lombok.Data;

/**
 * 数据库更新失败手动抛出异常
 *
 * @author: Yang
 * @date: 2020/3/4
 * @version: 1.0.0
 * Copyright Ⓒ 2021 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class UpdateFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public UpdateFailedException() {
        this.code = ResponseEnum.ERROR.getCode();
        this.msg = "数据更新失败，请稍后重新尝试";
    }

    public UpdateFailedException(String msg) {
        super(msg);
        this.code = ResponseEnum.ERROR.getCode();
        this.msg = msg;
    }

}
