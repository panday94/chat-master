package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字典信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class DictCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "缺少字典id", groups = UpdateGroup.class)
    @Min(value = 1, message = "字典id必须大于1", groups = UpdateGroup.class)
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String label;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String value;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否固定值 0->不固定 1->固定
     */
    private Integer isDefault;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
