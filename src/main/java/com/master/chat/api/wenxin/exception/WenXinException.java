package com.master.chat.api.wenxin.exception;

public class WenXinException extends RuntimeException {
    private String msg;

    public WenXinException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WenXinException() {
        super("未知错误");
        this.msg = "未知错误";
    }
}
