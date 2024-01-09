package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.DeptMapper;
import com.master.chat.sys.mapper.SysUserDeptMapper;
import com.master.chat.sys.pojo.entity.SysUserDept;
import com.master.chat.sys.service.ISysUserDeptService;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.StringPoolConstant;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户部门关联 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements ISysUserDeptService {
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public ResponseInfo<List<Long[]>> getSysUserDeptIds(Long sysUserId) {
        List<Long> deptIds = sysUserDeptMapper.getSysUserDeptIds(sysUserId);
        List<Long[]> result = new ArrayList<>();
        deptIds.stream().forEach(v -> {
            String[] str = deptMapper.selectById(v).getTreePath().split(StringPoolConstant.DASH);
            result.add((Long[]) ConvertUtils.convert(str, Long.class));
        });
        return ResponseInfo.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveSysUserDept(Long sysUserId, List<Long[]> deptIds, String operater) {
        sysUserDeptMapper.clearSysUserDept(sysUserId);
        deptIds.stream().forEach(v -> {
            int length = v.length;
            saveSysUserDept(sysUserId, v[length - 1], operater);
        });
        return ResponseInfo.success();
    }

    private Integer saveSysUserDept(Long sysUserId, Long deptId, String operater) {
        SysUserDept sysUserDept = SysUserDept.builder()
                .createUser(operater).sysUserId(sysUserId).deptId(deptId).build();
        return sysUserDeptMapper.insert(sysUserDept);
    }

}
