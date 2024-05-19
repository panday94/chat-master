package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色授权用户信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class SysUserRoleAuthCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id数组
     */
    private List<Long> userIds;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id数组
     */
    private List<Long> roleIds;

    /**
     * 是否为角色分配用户
     *
     * @return
     */
    public Boolean isUserIds() {
        return ValidatorUtil.isNotNullAndZero(roleId) && ValidatorUtil.isNotNullIncludeArray(userIds);
    }

}
