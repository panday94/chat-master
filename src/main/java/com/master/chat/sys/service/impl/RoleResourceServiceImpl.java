package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.RoleResourceMapper;
import com.master.chat.sys.service.IRoleResourceService;
import com.master.chat.sys.pojo.entity.RoleResource;
import com.master.common.api.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public ResponseInfo<List<Long>> getResourceIdByRoleId(Long id) {
        return ResponseInfo.success(roleResourceMapper.getResourceIdByRoleId(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo authorizationResource(List<Long> resourceIds, Long roleId, String operater) {
        roleResourceMapper.clearResourceByRoleId(roleId);
        roleResourceMapper.saveRoleResource(resourceIds, roleId, operater);
        roleResourceMapper.authorizationResource(resourceIds, roleId, operater);
        return ResponseInfo.success();
    }

}
