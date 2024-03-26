package com.master.chat.api.moonshot.exception;

public class MoonshotException extends RuntimeException {
    private String msg;

    public MoonshotException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MoonshotException() {
        super("未知错误");
        this.msg = "未知错误";
    }
}
