package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.DeptMapper;
import com.master.chat.sys.mapper.SysUserDeptMapper;
import com.master.chat.sys.pojo.command.DeptCommand;
import com.master.chat.sys.pojo.entity.Dept;
import com.master.chat.sys.service.IDeptService;
import com.master.chat.sys.pojo.entity.SysUserDept;
import com.master.chat.sys.pojo.vo.DeptVO;
import com.master.common.api.QueryDTO;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.StringPoolConstant;
import com.master.common.enums.StatusEnum;
import com.master.common.exception.ErrorException;
import com.master.common.utils.DozerUtil;
import com.master.common.utils.TreeUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门职位 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    private Dept getDept(Long id) {
        Dept dept = deptMapper.selectById(id);
        if (ValidatorUtil.isNull(dept)) {
            throw new ErrorException("部门信息不存在，无法操作");
        }
        return dept;
    }

    @Override
    public ResponseInfo<List<DeptVO>> treeDept(QueryDTO query) {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), Dept::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), Dept::getStatus, query.getStatus());
        return ResponseInfo.success(TreeUtil.tree(deptMapper.selectList(qw), DeptVO.class));
    }

    @Override
    public ResponseInfo<List<DeptVO>> listDept(QueryDTO query) {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), Dept::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), Dept::getStatus, query.getStatus());
        return ResponseInfo.success(DozerUtil.convertor(deptMapper.selectList(qw), DeptVO.class));
    }

    @Override
    public ResponseInfo<DeptVO> getDeptById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(deptMapper.selectById(id), DeptVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveDept(DeptCommand command) {
        Dept dept = DozerUtil.convertor(command, Dept.class);
        dept.setCreateUser(command.getOperater());
        deptMapper.insert(dept);
        updateDept(dept, command.getParentId());
        return ResponseInfo.success(dept.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateDept(DeptCommand command) {
        Dept dept = getDept(command.getId());
        dept.setUpdateUser(command.getOperater());
        DozerUtil.convertor(command, dept);
        updateDept(dept, command.getParentId());
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateDeptStatus(DeptCommand command) {
        Dept dept = getDept(command.getId());
        dept.setUpdateUser(command.getOperater());
        if (ValidatorUtil.isNull(command.getStatus())) {
            dept.setStatus(StatusEnum.ENABLED.getValue().equals(dept.getStatus()) ? StatusEnum.DISABLED.getValue() : StatusEnum.ENABLED.getValue());
        } else {
            dept.setStatus(command.getStatus());
        }
        deptMapper.updateById(dept);
        return ResponseInfo.success();
    }

    /**
     * 更新部门信息
     *
     * @param dept     部门信息
     * @param parentId 上级部门id
     */
    private void updateDept(Dept dept, Long parentId) {
        Dept parent = deptMapper.selectById(parentId);
        if (ValidatorUtil.isNotNull(parent)) {
            dept.setLevel(parent.getLevel() + 1);
            dept.setTreePath(parent.getTreePath() + StringPoolConstant.DASH + dept.getId());
        } else {
            dept.setTreePath(dept.getId().toString());
        }
        deptMapper.updateById(dept);
        if (ValidatorUtil.isNullOrZero(dept.getSysUserId()) || sysUserDeptMapper.selectCount(new LambdaQueryWrapper<SysUserDept>()
                .eq(SysUserDept::getDeptId, dept.getId()).eq(SysUserDept::getSysUserId, dept.getSysUserId()).eq(SysUserDept::getStatus, StatusEnum.ENABLED.getValue())) > 0) {
            return;
        }
        sysUserDeptMapper.insert(SysUserDept.builder()
                .createUser(dept.getUpdateUser())
                .deptId(dept.getId()).sysUserId(dept.getSysUserId()).status(StatusEnum.ENABLED.getValue()).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeById(Long id) {
        getDept(id);
        if (deptMapper.selectCount(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, id)) > 0) {
            return ResponseInfo.validateFail("当前部门下存在子部门，无法删除");
        }
        if (sysUserDeptMapper.selectCount(new LambdaQueryWrapper<SysUserDept>().eq(SysUserDept::getDeptId, id)) > 0) {
            return ResponseInfo.validateFail("当前部门下存在人员，无法删除");
        }
        deptMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
