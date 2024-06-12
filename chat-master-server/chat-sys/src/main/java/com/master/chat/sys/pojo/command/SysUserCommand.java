package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.framework.validator.group.UpdateGroup;
import com.master.chat.framework.xss.Xss;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * 账号信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SysUserCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "缺少角色id", groups = UpdateGroup.class)
    @Min(value = 1, message = "账号id必须大于1", groups = UpdateGroup.class)
    private Long id;

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
     * 昵称
     */
    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String tel;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 性别 0->女;1->男
     */
    private Integer gender;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 部门id信息
     */
    private List<Long[]> deptIds;

    /**
     * 角色id信息
     */
    private List<Long> roleIds;

    /**
     * 岗位id信息
     */
    private List<Long> postIds;

}
