package com.master.chat.sys.pojo.command;

import com.master.chat.common.xss.Xss;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 账号注册信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SysUserRegistCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Xss(message = "姓名不能包含脚本字符")
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 手机号
     */
    @Xss(message = "手机号不能包含脚本字符")
    @NotBlank(message = "手机号不能为空")
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String tel;

    /**
     * 用户名
     */
    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "缺少密码")
    @Size(min = 6, max = 30, message = "密码长度不能小于6个字符")
    private String password;

    /**
     * 密码
     */
    private String code;

    /**
     * 会话标识
     */
    private String uuid;

}
