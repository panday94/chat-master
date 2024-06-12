package com.master.chat.sys.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * 账号权限信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPreDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号id
     */
    private Long id;

    /**
     * 部门id数组
     */
    private Set<Long> deptIds;

    /**
     * 是否为部门负责人
     */
    private Boolean deptLeader;

}
