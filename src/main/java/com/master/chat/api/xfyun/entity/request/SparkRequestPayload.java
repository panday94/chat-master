package com.master.chat.api.xfyun.entity.request;

import java.io.Serializable;

/**
 * $.payload
 *
 * @author briqt
 */
public class SparkRequestPayload implements Serializable {
    private static final long serialVersionUID = 2084163918219863102L;

    private SparkRequestMessage message;

    public SparkRequestPayload() {
    }

    public SparkRequestPayload(SparkRequestMessage message) {
        this.message = message;
    }

    public SparkRequestMessage getMessage() {
        return message;
    }

    public void setMessage(SparkRequestMessage message) {
        this.message = message;
    }
}
