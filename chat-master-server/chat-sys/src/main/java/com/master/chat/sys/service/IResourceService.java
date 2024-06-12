package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.ResourceCommand;
import com.master.chat.sys.pojo.entity.Resource;
import com.master.chat.sys.pojo.vo.ResourceVO;
import com.master.chat.sys.pojo.vo.RouterVO;
import com.master.chat.client.model.dto.QueryDTO;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;
import java.util.Set;

/**
 * 资源 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface IResourceService extends IService<Resource> {

    /**
     * 获取资源列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<ResourceVO>> listResource(QueryDTO query);

    /**
     * 获取资源树结构
     *
     * @param isMenu    true 仅获取菜单资源 false 获取菜单和按钮所有资源
     * @param isButton  true 仅获取按钮资源 false 获取菜单和按钮所有资源
     * @param isEnabled true 仅获取启用的资源信息 false 获取禁用启用所有资源
     * @return
     */
    ResponseInfo<List<ResourceVO>> treeResource(Boolean isMenu, Boolean isButton, Boolean isEnabled);

    /**
     * 根据角色id获取菜单树结构
     *
     * @param roleIds 角色id数组
     * @param isAdmin 是否是管理员，管理员默认获取所有权限
     * @return
     */
    ResponseInfo<List<ResourceVO>> treeResource(List<Long> roleIds, Boolean isAdmin);

    /**
     * 根据角色id获取菜单树结构，选中已有权限
     *
     * @param roleId 角色
     * @return
     */
    ResponseInfo<List<ResourceVO>> treeResourceByRoleId(Long roleId);

    /**
     * 根据用户信息获取按钮资源
     *
     * @param sysUserId 用户id
     * @param username  用户名
     * @return
     */
    ResponseInfo<Set<String>> listButtonResourceBySysUser(Long sysUserId, String username);

    /**
     * 根据角色id获取按钮资源
     *
     * @param roleIds 角色id
     * @param isAdmin 是否为管理员
     * @return
     */
    ResponseInfo<Set<String>> listButtonResource(List<Long> roleIds, Boolean isAdmin);

    /**
     * 根据id获取资源信息
     *
     * @param id 资源id
     * @return
     */
    ResponseInfo<ResourceVO> getResourceById(Long id);

    /**
     * 添加菜单资源信息
     *
     * @param command 菜单资源信息
     * @return
     */
    ResponseInfo saveResource(ResourceCommand command);

    /**
     * 修改菜单资源信息
     *
     * @param command 菜单资源信息
     * @return
     */
    ResponseInfo updateResource(ResourceCommand command);

    /**
     * 启用/禁用菜单资源信息
     *
     * @param command 菜单资源信息
     * @return
     */
    ResponseInfo updateResourceStatus(ResourceCommand command);

    /**
     * 根据id删除资源信息
     *
     * @param id 资源id
     * @return
     */
    ResponseInfo removeResourceById(Long id);

    /**
     * 构建菜单返回信息
     *
     * @param resourceVOS 菜单信息
     * @return
     */
    List<RouterVO> buildResoureces(List<ResourceVO> resourceVOS);

}
