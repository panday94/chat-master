package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.sys.pojo.command.RoleCommand;
import com.master.chat.sys.pojo.entity.Role;
import com.master.chat.sys.pojo.vo.RoleVO;

import java.util.List;

/**
 * 角色 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface IRoleService extends IService<Role> {

    /**
     * 角色分页列表
     *
     * @param query 查询参数
     * @return
     */
    ResponseInfo<IPageInfo<RoleVO>> pageRole(Query query);

    /**
     * 角色列表
     *
     * @param query 查询参数
     * @return
     */
    ResponseInfo<List<RoleVO>> listRole(Query query);

    /**
     * 根据角色id获取角色列表
     *
     * @param ids 角色id
     * @return
     */
    ResponseInfo<List<RoleVO>> listRoleByIds(List<Long> ids);

    /**
     * 根据角色id查询角色详情
     *
     * @param id
     * @return
     */
    ResponseInfo<RoleVO> getRoleById(Long id);

    /**
     * 保存角色
     *
     * @param command 角色信息
     * @return
     */
    ResponseInfo saveRole(RoleCommand command);

    /**
     * 更新角色
     *
     * @param command 角色信息
     * @return
     */
    ResponseInfo updateRole(RoleCommand command);

    /**
     * 启用/禁用角色信息
     *
     * @param command 角色信息
     * @return
     */
    ResponseInfo updateRoleStatus(RoleCommand command);

    /**
     * 删除角色
     *
     * @param ids 角色id数组
     * @return
     */
    ResponseInfo removeByRoleIds(List<Long> ids);

}
