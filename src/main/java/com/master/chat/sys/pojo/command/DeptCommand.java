package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class DeptCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "缺少部门id", groups = UpdateGroup.class)
    @Min(value = 1, message = "部门id必须大于1", groups = UpdateGroup.class)
    private Long id;

    /**
     * 部门名称
     */
    @NotBlank(message = "缺少部门名称")
    private String name;

    /**
     * 部门负责人id
     */
    private Long sysUserId;

    /**
     * 父节点id
     */
    @NotNull(message = "缺少上级部门信息")
    private Long parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;


}
