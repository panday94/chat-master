package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 角色信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class RoleCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "缺少角色id", groups = UpdateGroup.class)
    @Min(value = 1, message = "角色id必须大于1", groups = UpdateGroup.class)
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    private String name;

    /**
     * 角色编码
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    private String code;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 资源id数组
     */
    private List<Long> resourceIds;

}
