package com.master.chat.sys.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 账号角色信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRolesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号id
     */
    private Long id;

    /**
     * 用户角色id
     */
    private List<Long> roleIds;

    /**
     * 是否为管理员账号
     */
    private Boolean admind;

}
