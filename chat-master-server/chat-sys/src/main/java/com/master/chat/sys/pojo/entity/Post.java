package com.master.chat.sys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  岗位
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
@TableName("sys_post")
public class Post extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 岗位名称
	 */
	private String name;

	/**
	 * 岗位编码
	 */
	private String code;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 状态 0->禁用;1->启用
	 */
	private Integer status;

	/**
	 * 是否删除 0->未删除;1->已删除
	 */
	@TableLogic
	private Integer deleted;


}
