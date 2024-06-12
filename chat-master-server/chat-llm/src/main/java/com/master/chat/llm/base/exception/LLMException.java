package com.master.chat.llm.base.exception;

import com.master.chat.common.enums.ResponseEnum;
import lombok.Getter;

/**
 * 大模型 异常处理
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public class LLMException extends RuntimeException {

    private static final long serialVersionUID = 3053312855506511893L;

    private Integer code;

    private String message;

    public LLMException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public LLMException(String message) {
        super(message);
        this.code = ResponseEnum.ERROR.getCode();
        this.message = message;
    }

    public LLMException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.message = message;
    }

}
