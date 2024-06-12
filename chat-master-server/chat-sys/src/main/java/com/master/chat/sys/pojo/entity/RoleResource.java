package com.master.chat.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  角色权限
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_resource")
public class RoleResource extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 资源权限id
	 */
	private Long resourceId;

	/**
	 * 状态 0->禁用;1->启用
	 */
	private Integer status;


}
