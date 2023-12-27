package com.master.chat.sys.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Master Computer Corporation Limited All rights reserved.
 */
@Data
public class DeptVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门负责人id
     */
    private Long sysUserId;

    /**
     * 父节点路径 如: 1; 1-2; 1-2-3
     */
    private String treePath;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

    /**
     * 子部门
     */
    private List<DeptVO> children;

}
