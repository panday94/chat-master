package com.master.chat.api.xfyun.entity;

import com.master.chat.api.xfyun.entity.response.SparkTextUsage;

import java.io.Serializable;

/**
 * 讯飞星火 响应内容
 *
 * @author: Yang
 * @date: 2023/09/06
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class SparkSyncChatResponse implements Serializable {
    private static final long serialVersionUID = -6785055441385392782L;

    /**
     * 回答内容
     */
    private String content;

    /**
     * tokens统计
     */
    private SparkTextUsage textUsage;

    /**
     * 内部自用字段
     */
    private boolean ok = false;

    private boolean success = false;

    private String errTxt;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SparkTextUsage getTextUsage() {
        return textUsage;
    }

    public void setTextUsage(SparkTextUsage textUsage) {
        this.textUsage = textUsage;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrTxt() {
        return errTxt;
    }

    public void setErrTxt(String errTxt) {
        this.errTxt = errTxt;
    }

}
