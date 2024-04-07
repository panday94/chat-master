package com.master.chat.common.enums;

import lombok.Getter;

/**
 * 全局返回参数
 *
 * @author: Yang
 * @date: 2020/8/6
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
public enum ResponseEnum {

    /**
     * 返回内容
     */
    SUCCESS(200, "操作成功"),
    SUCCESS_NODATE(204, "操作成功"),
    BAD_REQUEST(400, "参数错误"),
    PROHIBIT_VISIT(401, "账号认证失败，请重新登录"),
    PERMISSION_DENIED(403, "权限不足，无法操作"),
    RESOURCES_ERROR(404, "访问资源丢失，请升级相应版本或检查请求路径"),

    SING_ERROR(406, "签名验证失败"),
    REQUEST_METHOD_ERROR(407, "啊哟，请求方式错了哦，请确认API请求方式GET/POST/PUT/DELETE"),
    CONNECT_TIME_OUT(408, "连接超时啦，请稍后再试哦"),
    TOO_MANY_REQUESTS(429, "请求未受理，请降低频率后重试"),
    ERROR(500, "系统更新中，预计5分钟，若有问题请联系技术部"),
    BUSINESS_ERROR(600, "业务出错，请联系客服"),
    SYSTEM_WARNING(601, "系统预警，请及时处理"),

    /**
     * 内部错误码
     */
    NO_LOGIN(4011, "未登录"),

    ACCOUNT_LOGIN_EXIST(4011, "该账号已在其他地方登录，请重新登录"),

    ACCOUNT_NOT_EXIST(4012, "账号不存在，请先注册或联系管理员创建"),

    ACCOUNT_IS_DISABLED(4013, "账号已被禁用，请联系管理员"),

    NAME_IS_EXIST(4014, "账号已存在，请核对账号信息"),

    TEL_IS_EXIST(4015, "该手机号已经注册，请直接登录"),

    PHONE_BINDING(4016, "请先绑定手机号"),

    PASSWORD_ERROR(4017, "账号密码错误"),

    SMS_ERROR(4018, "验证码错误"),

    REPEAT_REQUEST_SMS(4019, "验证码有限期为5分钟，无需重复获取"),

    FILE_ERROR(6010, "文件处理失败，请稍后再试");
    private final Integer code;
    private final String msg;

    ResponseEnum(final Integer code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

}
