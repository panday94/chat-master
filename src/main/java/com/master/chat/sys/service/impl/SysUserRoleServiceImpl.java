package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.SysUserMapper;
import com.master.chat.sys.mapper.SysUserRoleMapper;
import com.master.chat.sys.pojo.dto.SysUserRolesDTO;
import com.master.chat.sys.pojo.entity.SysUser;
import com.master.chat.sys.pojo.entity.SysUserRole;
import com.master.chat.sys.service.ISysUserRoleService;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.StatusEnum;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.master.chat.common.constant.Constants.ADMIN;

/**
 * 用户角色 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseInfo<List<Long>> getSysUserRoleIds(Long sysUserId) {
        return ResponseInfo.success(sysUserRoleMapper.getSysUserRoleIds(sysUserId));
    }

    @Override
    public ResponseInfo<SysUserRolesDTO> getSysUserRoles(Long sysUserId, String username) {
        SysUser sysUser = sysUserMapper.selectById(sysUserId);
        if (ValidatorUtil.isNull(sysUser)) {
            return ResponseInfo.unauthorized();
        }
        List<Long> roleIds = getSysUserRoleIds(sysUserId).getData();
        Boolean admind = ADMIN.equals(username) ? true : false || sysUser.getAdmind();
        return ResponseInfo.success(SysUserRolesDTO.builder().id(sysUserId).roleIds(roleIds).admind(admind).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveSysUserRole(Long sysUserId, Long roleId, String operater) {
        clearSysUserRole(sysUserId);
        return insertSysUserRole(sysUserId, roleId, operater);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveSysUserRole(Long sysUserId, List<Long> roleIds, String operater) {
        clearSysUserRole(sysUserId);
        roleIds.stream().forEach(v -> {
            insertSysUserRole(sysUserId, v, operater);
        });
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveSysUserRole(List<Long> sysUserIds, Long roleId, String operater) {
        sysUserIds.stream().forEach(v -> {
            insertSysUserRole(v, roleId, operater);
        });
        return ResponseInfo.success();
    }

    /**
     * 添加用户角色信息
     *
     * @param sysUserId 用户id
     * @param roleId    角色id
     * @param operater  操作人
     * @return
     */
    private ResponseInfo insertSysUserRole(Long sysUserId, Long roleId, String operater) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.lambda().eq(SysUserRole::getSysUserId, sysUserId).eq(SysUserRole::getRoleId, roleId);
        SysUserRole userRole = sysUserRoleMapper.selectOne(qw);
        if (ValidatorUtil.isNull(userRole)) {
            userRole = SysUserRole.builder().createUser(operater).sysUserId(sysUserId).roleId(roleId).build();
            sysUserRoleMapper.insert(userRole);
            return ResponseInfo.success();
        }
        if (StatusEnum.ENABLED.getValue().equals(userRole.getStatus())) {
            return ResponseInfo.success();
        }
        userRole.setStatus(StatusEnum.ENABLED.getValue());
        sysUserRoleMapper.updateById(userRole);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo unauthorizationSysUser(List<Long> sysUserIds, Long roleId, String operater) {
        sysUserRoleMapper.unauthorizationSysUser(sysUserIds, roleId, operater);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo clearSysUserRole(Long sysUserId) {
        sysUserRoleMapper.clearSysUserRole(sysUserId);
        return ResponseInfo.success();
    }

}
