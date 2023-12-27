package com.master.chat.sys.pojo.command;

import com.master.common.api.CommonCommand;
import com.master.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 岗位信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class PostCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "缺少岗位id", groups = UpdateGroup.class)
    @Min(value = 1, message = "岗位id必须大于1", groups = UpdateGroup.class)
    private Long id;

    /**
     * 岗位名称
     */
    @NotNull(message = "缺少岗位名称")
    private String name;

    /**
     * 岗位编码
     */
    @NotNull(message = "缺少岗位编码")
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

}
