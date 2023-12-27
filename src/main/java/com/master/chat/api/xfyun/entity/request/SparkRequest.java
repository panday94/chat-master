package com.master.chat.api.xfyun.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.entity.SparkRequestBuilder;

import java.io.Serializable;

/**
 * SparkRequest
 *
 * @author briqt
 */
public class SparkRequest implements Serializable {
    private static final long serialVersionUID = 8142547165395379456L;

    private SparkRequestHeader header;

    private SparkRequestParameter parameter;

    private SparkRequestPayload payload;

    private transient SparkApiVersion apiVersion = SparkApiVersion.V2_0;

    public static SparkRequestBuilder builder() {
        return new SparkRequestBuilder();
    }

    public SparkRequestHeader getHeader() {
        return header;
    }

    public void setHeader(SparkRequestHeader header) {
        this.header = header;
    }

    public SparkRequestParameter getParameter() {
        return parameter;
    }

    public void setParameter(SparkRequestParameter parameter) {
        this.parameter = parameter;
    }

    public SparkRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(SparkRequestPayload payload) {
        this.payload = payload;
    }

    @JsonIgnore
    public SparkApiVersion getApiVersion() {
        return apiVersion;
    }

    @JsonIgnore
    public void setApiVersion(SparkApiVersion apiVersion) {
        this.apiVersion = apiVersion;
    }
}
