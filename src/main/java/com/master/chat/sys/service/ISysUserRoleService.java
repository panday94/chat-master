package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.dto.SysUserRolesDTO;
import com.master.chat.sys.pojo.entity.SysUserRole;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户关联角色id数组
     *
     * @param sysUserId 用户id
     * @return
     */
    ResponseInfo<List<Long>> getSysUserRoleIds(Long sysUserId);

    /**
     * 根据用户id获取用户角色详情
     *
     * @param sysUserId 账号id
     * @param username  用户名
     * @return
     */
    ResponseInfo<SysUserRolesDTO> getSysUserRoles(Long sysUserId, String username);

    /**
     * 授权用户角色（一对一）
     * 清除原有角色
     *
     * @param sysUserId 用户id
     * @param roleId    角色id
     * @param operater  操作人
     * @return
     */
    ResponseInfo saveSysUserRole(Long sysUserId, Long roleId, String operater);

    /**
     * 授权用户角色（一对多
     * 清除原有角色
     *
     * @param sysUserId 用户id
     * @param roleIds   多个角色id数组
     * @param operater  操作人
     * @return
     */
    ResponseInfo saveSysUserRole(Long sysUserId, List<Long> roleIds, String operater);

    /**
     * 授权用户角色（多对一）
     * 无需清除原有角色
     *
     * @param sysUserIds 多个用户id数组
     * @param roleId     角色id
     * @param operater   操作人
     * @return
     */
    ResponseInfo saveSysUserRole(List<Long> sysUserIds, Long roleId, String operater);

    /**
     * 取消授权用户
     *
     * @param sysUserIds 多个用户id数组
     * @param roleId     角色id
     * @param operater   操作人
     * @return
     */
    ResponseInfo unauthorizationSysUser(List<Long> sysUserIds, Long roleId, String operater);

    /**
     * 清空用户角色
     *
     * @param sysUserId 用户id
     * @return
     */
    ResponseInfo clearSysUserRole(Long sysUserId);

}
