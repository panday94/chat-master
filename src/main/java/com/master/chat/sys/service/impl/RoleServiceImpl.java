package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.sys.mapper.RoleMapper;
import com.master.chat.sys.mapper.SysUserRoleMapper;
import com.master.chat.sys.pojo.command.RoleCommand;
import com.master.chat.sys.pojo.entity.Role;
import com.master.chat.sys.pojo.entity.SysUserRole;
import com.master.chat.sys.pojo.vo.RoleVO;
import com.master.chat.sys.service.IRoleResourceService;
import com.master.chat.sys.service.IRoleService;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.LongEnum;
import com.master.common.enums.StatusEnum;
import com.master.common.exception.ErrorException;
import com.master.common.utils.DozerUtil;
import com.master.common.validator.ValidatorUtil;
import com.master.common.validator.base.BaseAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private IRoleResourceService roleResourceService;

    /**
     * 获取角色信息
     *
     * @param id 角色id
     * @return
     */
    private Role getRole(Long id) {
        Role role = roleMapper.selectById(id);
        if (ValidatorUtil.isNull(role)) {
            throw new ErrorException("角色信息不存在，无法修改");
        }
        return role;
    }

    /**
     * 拼接查询条件
     *
     * @param query 查询条件
     * @return
     */
    private QueryWrapper<Role> getQw(Query query) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.lambda().gt(BaseEntity::getId, LongEnum.ONE.getValue());
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), Role::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("code")), Role::getCode, query.get("code"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), Role::getStatus, query.getStatus());
        qw.lambda().ge(ValidatorUtil.isNotNull(query.getStartDate()), Role::getCreateTime, query.getStartDate());
        qw.lambda().le(ValidatorUtil.isNotNull(query.getEndDate()), Role::getCreateTime, query.getEndDate());
        qw.lambda().orderByDesc(BaseEntity::getId);
        return qw;
    }

    @Override
    public ResponseInfo<IPageInfo<RoleVO>> pageRole(Query query) {
        IPage<Role> iPage = roleMapper.selectPage(new Page<Role>(query.getCurrent(), query.getSize()), getQw(query));
        IPageInfo<RoleVO> result = IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), RoleVO.class);
        if (ValidatorUtil.isNotNull(query.get("userId"))) {
            List<Long> roleIds = userRoleMapper.getSysUserRoleIds(BaseAssert.getParamLong(query, "userId"));
            result.getRecords().stream().forEach(v -> {
                if (ValidatorUtil.isNotNull(roleIds) && roleIds.contains(v.getId())) {
                    v.setFlag(true);
                }
            });
        }
        return ResponseInfo.success(result);
    }

    @Override
    public ResponseInfo<List<RoleVO>> listRole(Query query) {
        return ResponseInfo.success(DozerUtil.convertor(roleMapper.selectList(getQw(query)), RoleVO.class));
    }

    @Override
    public ResponseInfo<List<RoleVO>> listRoleByIds(List<Long> ids) {
        return ResponseInfo.success(DozerUtil.convertor(roleMapper.listByRoleIds(ids), RoleVO.class));
    }

    @Override
    public ResponseInfo<RoleVO> getRoleById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getRole(id), RoleVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveRole(RoleCommand command) {
        Role role = DozerUtil.convertor(command, Role.class);
        role.setCreateUser(command.getOperater());
        roleMapper.insert(role);
        authorizationResource(command, role.getId());
        return ResponseInfo.success(role.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateRole(RoleCommand command) {
        Role role = getRole(command.getId());
        DozerUtil.convertor(command, role);
        role.setUpdateUser(command.getOperater());
        roleMapper.updateById(role);
        authorizationResource(command, role.getId());
        return ResponseInfo.success();
    }

    /**
     * 授权用户资源信息
     *
     * @param command 角色信息
     * @param roleId  角色id
     */
    private void authorizationResource(RoleCommand command, Long roleId) {
        if (ValidatorUtil.isNotNullIncludeArray(command.getResourceIds())) {
            roleResourceService.authorizationResource(command.getResourceIds(), roleId, command.getOperater());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateRoleStatus(RoleCommand command) {
        Role role = getRole(command.getId());
        role.setUpdateUser(command.getOperater());
        if (ValidatorUtil.isNull(command.getStatus())) {
            role.setStatus(StatusEnum.ENABLED.getValue().equals(role.getStatus()) ? StatusEnum.DISABLED.getValue() : StatusEnum.ENABLED.getValue());
        } else {
            role.setStatus(command.getStatus());
        }
        roleMapper.updateById(role);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeByRoleIds(List<Long> ids) {
        if (userRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getRoleId, ids)) > 0) {
            return ResponseInfo.businessFail("角色下存在用户，无法删除");
        }
        roleMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

}
