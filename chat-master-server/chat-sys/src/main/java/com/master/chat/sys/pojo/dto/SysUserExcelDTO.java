package com.master.chat.sys.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.master.chat.sys.converter.CustomStringStringConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户信息导出对象
 *
 * @author: Yang
 * @date: 2023/01/31
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Getter
@Setter
@ToString
@HeadRowHeight(20)
@ColumnWidth(25)
@ContentRowHeight(18)
@EqualsAndHashCode
public class SysUserExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "用户编号")
    private Long id;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String createUser;

    /**
     * 登录时间
     */
    @ExcelProperty(value = "登录时间")
    private String loginTime;

    /**
     * 用户名
     */
    @ColumnWidth(30)
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String tel;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 性别 0->女;1->男
     */
    @ExcelProperty(value = "性别", converter = CustomStringStringConverter.class)
    private String gender;

}
