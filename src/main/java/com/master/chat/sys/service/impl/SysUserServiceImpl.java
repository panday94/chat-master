package com.master.chat.sys.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.comm.constant.ResourceConstants;
import com.master.chat.framework.security.JWTPasswordEncoder;
import com.master.chat.sys.mapper.DeptMapper;
import com.master.chat.sys.mapper.SysUserMapper;
import com.master.chat.sys.pojo.command.SysUserCommand;
import com.master.chat.sys.pojo.command.SysUserPasswordCommand;
import com.master.chat.sys.pojo.dto.SysUserPreDTO;
import com.master.chat.sys.pojo.dto.SysUserRolesDTO;
import com.master.chat.sys.pojo.entity.Dept;
import com.master.chat.sys.pojo.entity.SysUser;
import com.master.chat.sys.pojo.vo.ContactUserVO;
import com.master.chat.sys.pojo.vo.DeptVO;
import com.master.chat.sys.pojo.vo.RoleVO;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.sys.service.*;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.LongEnum;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.exception.PermissionException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 员工账号 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    private static final String ADMIN = "admin";
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysUserPostService sysUserPostService;
    @Autowired
    private ISysUserDeptService sysUserDeptService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 根据id获取用户信息
     *
     * @param id 用户id
     * @return
     */
    private SysUser getSysUser(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (ValidatorUtil.isNull(sysUser)) {
            throw new ErrorException("账号信息不存在，无法操作");
        }
        return sysUser;
    }

    /**
     * 获取系统用户信息
     *
     * @param sysUser 系统用户
     * @return
     */
    private SysUserVO getSysUserVO(SysUser sysUser) {
        if (ValidatorUtil.isNull(sysUser)) {
            return null;
        }
        SysUserVO userVO = DozerUtil.convertor(sysUser, SysUserVO.class);
        SysUserRolesDTO sysUserRole = sysUserRoleService.getSysUserRoles(sysUser.getId(), sysUser.getUsername()).getData();
        userVO.setRoleIds(sysUserRole.getRoleIds());
        userVO.setDeptIds(sysUserDeptService.getSysUserDeptIds(sysUser.getId()).getData());
        userVO.setPostIds(sysUserPostService.getSysUserPostIds(sysUser.getId()).getData());
        if (ValidatorUtil.isNotNullIncludeArray(userVO.getDeptIds())) {
            userVO.setDeptName(deptMapper.selectById(userVO.getDeptIds().get(0)[0]).getName());
        }
        Set<String> permissions = resourceService.listButtonResource(sysUserRole.getRoleIds(), sysUserRole.getAdmind()).getData();
        if (sysUserRole.getAdmind()) {
            permissions.add(ResourceConstants.ADMIN_PERMISSIONS);
        }
        List<RoleVO> roleVOS = roleService.listRoleByIds(sysUserRole.getRoleIds()).getData();
        Set<String> roles = new HashSet<>();
        roleVOS.stream().forEach(v -> roles.add(v.getCode()));
        userVO.setRoles(roles);
        userVO.setPermissions(permissions);
        return userVO;
    }

    @Override
    public ResponseInfo<IPageInfo<SysUserVO>> pageSysUser(Query query) {
        IPage<SysUserVO> iPage = sysUserMapper.pageSysUser(new Page<SysUserVO>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<SysUserVO>> listSysUser(Query query) {
        return ResponseInfo.success(sysUserMapper.listSysUser(query));
    }

    @Override
    public ResponseInfo<List<ContactUserVO>> listContactSysUser(Query query) {
        return ResponseInfo.success(sysUserMapper.listContactSysUser(query));
    }

    @Override
    public ResponseInfo<SysUserVO> getSysUserById(Long id) {
        return ResponseInfo.success(getSysUserVO(getSysUser(id)));
    }

    @Override
    public ResponseInfo<SysUserVO> getSysUserByUsername(String username) {
        return ResponseInfo.success(getSysUserVO(sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveSysUser(SysUserCommand command) {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, command.getUsername()));
        if (ValidatorUtil.isNotNull(sysUser)) {
            return ResponseInfo.customizeError(ResponseEnum.NAME_IS_EXIST);
        }
        sysUser = DozerUtil.convertor(command, SysUser.class);
        sysUser.setCreateUser(command.getOperater());
        sysUser.setUid(UUID.randomUUID().toString());
        if (ValidatorUtil.isNull(sysUser.getNickName())) {
            sysUser.setNickName(sysUser.getName());
        }
        sysUser.setAdmind(false);
        sysUser.setPassword(JWTPasswordEncoder.bcryptEncode(command.getPassword()));
        sysUserMapper.insert(sysUser);
        saveUserInfo(command, sysUser.getId());
        return ResponseInfo.success(sysUser.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateSysUser(SysUserCommand command) {
        SysUser sysUser = getSysUser(command.getId());
        if (ADMIN.equals(sysUser.getUsername()) && !sysUser.getId().equals(command.getId())) {
            throw new PermissionException();
        }
        command.setPassword(null);
        DozerUtil.convertor(command, sysUser);
        sysUser.setUpdateUser(command.getOperater());
        sysUserMapper.updateById(sysUser);
        if (ADMIN.equals(sysUser.getUsername())) {
            return ResponseInfo.success();
        }
        saveUserInfo(command, sysUser.getId());
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateSysUserAvatar(Long id, String avatar) {
        SysUser sysUser = getSysUser(id);
        sysUser.setAvatar(avatar);
        sysUserMapper.updateById(sysUser);
        return ResponseInfo.success(avatar);
    }

    /**
     * 更新用户信息
     *
     * @param command 用户信息
     * @param userId  用户id
     */
    private void saveUserInfo(SysUserCommand command, Long userId) {
        if (ValidatorUtil.isNotNullAndZero(command.getRoleId())) {
            sysUserRoleService.saveSysUserRole(userId, command.getRoleId(), command.getOperater());
        }
        if (ValidatorUtil.isNotNullIncludeArray(command.getRoleIds())) {
            sysUserRoleService.saveSysUserRole(userId, command.getRoleIds(), command.getOperater());
        }
        if (ValidatorUtil.isNotNullIncludeArray(command.getDeptIds())) {
            sysUserDeptService.saveSysUserDept(userId, command.getDeptIds(), command.getOperater());
        }
        if (ValidatorUtil.isNotNullIncludeArray(command.getPostIds())) {
            sysUserPostService.saveSysUserPost(userId, command.getPostIds(), command.getOperater());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateSysUserStatus(SysUserCommand command) {
        SysUser sysUser = getSysUser(command.getId());
        if (ADMIN.equals(sysUser.getUsername())) {
            throw new PermissionException("管理员账号无法操作");
        }
        sysUser.setUpdateUser(command.getOperater());
        if (ValidatorUtil.isNull(command.getStatus())) {
            sysUser.setStatus(StatusEnum.ENABLED.getValue().equals(sysUser.getStatus()) ? StatusEnum.DISABLED.getValue() : StatusEnum.ENABLED.getValue());
        } else {
            sysUser.setStatus(command.getStatus());
        }
        sysUserMapper.updateById(sysUser);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo resetPassword(SysUserPasswordCommand command) {
        SysUser sysUser = getSysUser(command.getId());
        sysUser.setPassword(JWTPasswordEncoder.bcryptEncode(command.getPassword()));
        sysUserMapper.updateById(sysUser);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updatePassword(SysUserPasswordCommand command) {
        SysUser sysUser = getSysUser(command.getOperaterId());
        boolean isBcrypt = JWTPasswordEncoder.matchesBcrypt(sysUser.getPassword());
        if (isBcrypt) {
            if (!JWTPasswordEncoder.matchesBcrypt(command.getOldPassword(), sysUser.getPassword())) {
                return ResponseInfo.businessFail("旧密码错误，您还有5次机会或者联系管理员重置密码");
            }
            if (JWTPasswordEncoder.matchesBcrypt(command.getNewPassword(), sysUser.getPassword())) {
                return ResponseInfo.businessFail("新密码与旧密码相同，无需重置");
            }
        } else {
            if (!command.getOldPassword().equals(sysUser.getPassword())) {
                return ResponseInfo.businessFail("旧密码错误，您还有5次机会或者联系管理员重置密码");
            }
            if (command.getNewPassword().equals(sysUser.getPassword())) {
                return ResponseInfo.businessFail("新密码与旧密码相同，无需重置");
            }
        }
        sysUser.setPassword(JWTPasswordEncoder.bcryptEncode(command.getNewPassword()));
        sysUserMapper.updateById(sysUser);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateLoginTime(Long id) {
        sysUserMapper.updateLoginTime(id);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo sysUserAdmind(Long id) {
        SysUser sysUser = new SysUser().setAdmind(true);
        sysUserMapper.update(sysUser, new LambdaUpdateWrapper<SysUser>().eq(SysUser::getId, id));
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeBySysUserIds(List<Long> ids) {
        if (ids.contains(LongEnum.ONE.getValue())) {
            throw new PermissionException("管理员账号无法删除");
        }
        sysUserMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    public SysUserPreDTO getDeptPermissionsInfo(Long id) {
        List<DeptVO> deptVOS = deptMapper.listDeptBySysUserId(id);
        if (ValidatorUtil.isNullIncludeArray(deptVOS)) {
            return SysUserPreDTO.builder().id(id).deptLeader(false).build();
        }
        Set<Long> ids = new HashSet<>();
        Boolean deptLeader = false;
        for (DeptVO dept : deptVOS) {
            List<Dept> childDept;
            if (dept.getParentId() == 0) {
                childDept = deptMapper.selectList(new QueryWrapper<Dept>().likeRight("tree_path", dept.getId() + "-"));
            } else {
                childDept = deptMapper.selectList(new QueryWrapper<Dept>().like("tree_path", "-" + dept.getId() + "-"));
            }
            if (ValidatorUtil.isNotNullIncludeArray(childDept)) {
                deptLeader = true;
                ids.addAll(childDept.stream().map(Dept::getId).collect(Collectors.toList()));
            }
            if (id.equals(dept.getSysUserId())) {
                deptLeader = true;
                ids.add(dept.getId());
            }
        }
        return SysUserPreDTO.builder().id(id).deptIds(ids).deptLeader(deptLeader).build();
    }

}
