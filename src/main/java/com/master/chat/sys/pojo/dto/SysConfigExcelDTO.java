package com.master.chat.sys.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 系统配置信息导出对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Master Computer Corporation Limited All rights reserved.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SysConfigExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "参数id")
    private Long id;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String createUser;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String createTime;

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    private String name;

    /**
     * 参数键名
     */
    @ExcelProperty(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @ExcelProperty(value = "参数键值")
    private String configValue;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
