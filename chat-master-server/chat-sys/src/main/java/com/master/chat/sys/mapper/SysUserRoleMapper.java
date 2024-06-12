package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id获取用户角色数组
     *
     * @param sysUserId 用户id
     * @return
     */
    List<Long> getSysUserRoleIds(@Param("sysUserId") Long sysUserId);

    /**
     * 清空用户角色信息
     *
     * @param sysUserId 用户id
     */
    void clearSysUserRole(@Param("sysUserId") Long sysUserId);

    /**
     * 取消用户授权
     *
     * @param sysUserIds 用户id数组
     * @param roleId     角色id
     * @param operater   操作人
     */
    void unauthorizationSysUser(@Param("sysUserIds") List<Long> sysUserIds, @Param("roleId") Long roleId, @Param("operater") String operater);

}
