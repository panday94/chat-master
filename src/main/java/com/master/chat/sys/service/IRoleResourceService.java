package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.entity.RoleResource;
import com.master.common.api.ResponseInfo;

import java.util.List;

/**
 * 角色权限 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IRoleResourceService extends IService<RoleResource> {

    /**
     * 根据角色id获取资源id信息
     *
     * @param id 角色id
     * @return
     */
    ResponseInfo<List<Long>> getResourceIdByRoleId(Long id);

    /**
     * 角色授权资源信息
     *
     * @param resourceIds 资源id数组
     * @param roleId      角色id
     * @param operater    操作人
     * @return
     */
    ResponseInfo authorizationResource(List<Long> resourceIds, Long roleId, String operater);

}
