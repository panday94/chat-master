package com.master.chat.sys.pojo.command;

import com.master.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色授权资源信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class RoleResourceAuthCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源id数组
     */
    private List<Long> resourceIds;

}
