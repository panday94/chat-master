package com.master.chat.common.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.master.chat.common.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author: Yang
 * @date: 2020/11/18
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class ResponseInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回提示
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public ResponseInfo() {
    }

    public ResponseInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseInfo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 判断是否成功
     */
    @JsonIgnore
    public Boolean isSuccess() {
        return ResponseEnum.SUCCESS.getCode().equals(this.code);
    }

    /**
     * 成功
     */
    public static ResponseInfo success() {
        return new ResponseInfo(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    /**
     * 成功
     *
     * @param msg 返回提示
     */
    public static ResponseInfo successMsg(String msg) {
        return new ResponseInfo(ResponseEnum.SUCCESS.getCode(), msg);
    }

    /**
     * 成功
     *
     * @param data 返回结果
     */
    public static ResponseInfo success(Object data) {
        return new ResponseInfo(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 参数验证失败返回
     */
    public static ResponseInfo validateFail() {
        return new ResponseInfo(ResponseEnum.BAD_REQUEST.getCode(), ResponseEnum.BAD_REQUEST.getMsg());
    }

    /**
     * 参数验证失败返回结果
     *
     * @param msg 提示信息
     */
    public static ResponseInfo validateFail(String msg) {
        return new ResponseInfo(ResponseEnum.BAD_REQUEST.getCode(), msg);
    }

    /**
     * 访问禁止返回（token失效）
     */
    public static ResponseInfo unauthorized() {
        return new ResponseInfo(ResponseEnum.PROHIBIT_VISIT.getCode(), ResponseEnum.PROHIBIT_VISIT.getMsg());
    }

    /**
     * 访问禁止返回
     *
     * @param msg 返回提示
     */
    public static ResponseInfo unauthorized(String msg) {
        return new ResponseInfo(ResponseEnum.PROHIBIT_VISIT.getCode(), msg);
    }

    /**
     * 权限不足返回
     */
    public static ResponseInfo permissionDenied() {
        return new ResponseInfo(ResponseEnum.PERMISSION_DENIED.getCode(), ResponseEnum.PERMISSION_DENIED.getMsg());
    }

    /**
     * 权限不足返回
     *
     * @param msg 返回提示
     */
    public static ResponseInfo permissionDenied(String msg) {
        return new ResponseInfo(ResponseEnum.PERMISSION_DENIED.getCode(), msg);
    }

    /**
     * 访问资源丢失返回
     */
    public static ResponseInfo resourcesError() {
        return new ResponseInfo(ResponseEnum.RESOURCES_ERROR.getCode(), ResponseEnum.RESOURCES_ERROR.getMsg());
    }

    /**
     * 请求方式错误
     */
    public static ResponseInfo requestMethodError() {
        return new ResponseInfo(ResponseEnum.REQUEST_METHOD_ERROR.getCode(), ResponseEnum.REQUEST_METHOD_ERROR.getMsg());
    }

    /**
     * 连接超时
     */
    public static ResponseInfo connectTimeOut() {
        return new ResponseInfo(ResponseEnum.CONNECT_TIME_OUT.getCode(), ResponseEnum.CONNECT_TIME_OUT.getMsg());
    }

    /**
     * 请求频繁
     */
    public static ResponseInfo requestTooMany() {
        return new ResponseInfo(ResponseEnum.TOO_MANY_REQUESTS.getCode(), ResponseEnum.TOO_MANY_REQUESTS.getMsg());
    }

    /**
     * 账号不存在
     */
    public static ResponseInfo accountNotExist() {
        return new ResponseInfo(ResponseEnum.ACCOUNT_NOT_EXIST.getCode(), ResponseEnum.ACCOUNT_NOT_EXIST.getMsg());
    }

    /**
     * 密码错误
     */
    public static ResponseInfo passwordError() {
        return new ResponseInfo(ResponseEnum.PASSWORD_ERROR.getCode(), ResponseEnum.PASSWORD_ERROR.getMsg());
    }

    /**
     * 账号已禁用
     */
    public static ResponseInfo accountDisabled() {
        return new ResponseInfo(ResponseEnum.ACCOUNT_IS_DISABLED.getCode(), ResponseEnum.ACCOUNT_IS_DISABLED.getMsg());
    }

    /**
     * 手机号已存在
     */
    public static ResponseInfo telError() {
        return new ResponseInfo(ResponseEnum.TEL_IS_EXIST.getCode(), ResponseEnum.TEL_IS_EXIST.getMsg());
    }

    /**
     * 用户名存在
     */
    public static ResponseInfo nameError() {
        return new ResponseInfo(ResponseEnum.NAME_IS_EXIST.getCode(), ResponseEnum.NAME_IS_EXIST.getMsg());
    }

    /**
     * 系统异常
     */
    public static ResponseInfo error() {
        return new ResponseInfo(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getMsg());
    }

    /**
     * 系统异常
     *
     * @param msg 返回提示
     */
    public static ResponseInfo error(String msg) {
        return new ResponseInfo(ResponseEnum.ERROR.getCode(), msg);
    }

    /**
     * 业务异常
     */
    public static ResponseInfo businessFail() {
        return new ResponseInfo(ResponseEnum.BUSINESS_ERROR.getCode(), ResponseEnum.BUSINESS_ERROR.getMsg());
    }

    /**
     * 业务异常
     *
     * @param msg 返回提示
     */
    public static ResponseInfo businessFail(String message) {
        return new ResponseInfo(ResponseEnum.BUSINESS_ERROR.getCode(), message);
    }

    /**
     * 业务异常
     *
     * @param msg  返回提示
     * @param data 返回结果
     */
    public static ResponseInfo businessFail(String message, Object data) {
        return new ResponseInfo(ResponseEnum.BUSINESS_ERROR.getCode(), message, data);
    }

    /**
     * 系统预警
     */
    public static ResponseInfo systemWarning() {
        return new ResponseInfo(ResponseEnum.SYSTEM_WARNING.getCode(), ResponseEnum.SYSTEM_WARNING.getMsg());
    }

    /**
     * 系统预警
     *
     * @param msg 返回提示
     */
    public static ResponseInfo systemWarning(String message) {
        return new ResponseInfo(ResponseEnum.SYSTEM_WARNING.getCode(), message);
    }

    /**
     * 自定义异常
     *
     * @param code 返回码
     * @param msg  返回提示
     * @param data 返回结果
     */
    public static ResponseInfo customizeError(ResponseEnum response) {
        return new ResponseInfo(response.getCode(), response.getMsg());
    }

    /**
     * 自定义异常
     *
     * @param code 返回码
     * @param msg  返回提示
     */
    public static ResponseInfo customizeError(Integer code, String msg) {
        return new ResponseInfo(code, msg);
    }

    /**
     * 自定义异常
     *
     * @param code 返回码
     * @param msg  返回提示
     * @param data 返回结果
     */
    public static ResponseInfo customizeError(Integer code, String msg, Object data) {
        return new ResponseInfo(code, msg, data);
    }

}
