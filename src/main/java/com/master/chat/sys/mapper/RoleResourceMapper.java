package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.RoleResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    /**
     * 根据角色id获取资源信息
     *
     * @param id 角色id
     * @return
     */
    List<Long> getResourceIdByRoleId(@Param("id") Long id);

    /**
     * 根据角色id数组获取资源信息
     *
     * @param ids 角色id数组
     * @return
     */
    List<Long> getResourceIdByRoleIds(@Param("ids") Long ids);

    /**
     * 添加未添加过的角色资源信息
     *
     * @param resourceIds 资源id数组
     * @param roleId      角色id
     * @param operater    操作人
     * @return
     */
    void saveRoleResource(@Param("resourceIds") List<Long> resourceIds, @Param("roleId") Long roleId, @Param("operater") String operater);

    /**
     * 清空角色资源信息
     *
     * @param roleId 角色id
     */
    void clearResourceByRoleId(@Param("roleId") Long roleId);


    /**
     * 授权角色资源信息
     *
     * @param resourceIds 资源id数组
     * @param roleId      角色id
     * @param operater    操作人
     */
    void authorizationResource(@Param("resourceIds") List<Long> resourceIds, @Param("roleId") Long roleId, @Param("operater") String operater);

}
