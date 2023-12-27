package com.master.chat.api.xfyun.exception;

import com.master.chat.api.xfyun.constant.SparkErrorCode;

/**
 * 讯飞星火 异常处理
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class SparkException extends RuntimeException {

    private static final long serialVersionUID = 3053312855506511893L;

    private Integer code;

    private String sid;

    public SparkException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SparkException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public static SparkException bizFailed(Integer code) {
        String errorMessage = SparkErrorCode.RESPONSE_ERROR_MAP.get(code);
        if (null == errorMessage) {
            errorMessage = "未知的错误码";
        }
        return new SparkException(code, errorMessage);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
