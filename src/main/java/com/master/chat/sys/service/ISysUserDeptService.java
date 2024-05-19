package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.entity.SysUserDept;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 用户部门关联 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ISysUserDeptService extends IService<SysUserDept> {

    /**
     * 获取用户关联部门id信息
     *
     * @param sysUserId
     * @return
     */
    ResponseInfo<List<Long[]>> getSysUserDeptIds(Long sysUserId);

    /**
     * 添加用户部门（一对多）
     *
     * @param sysUserId 用户id
     * @param deptIds   多个岗位id数组
     * @param operater  操作人
     * @return
     */
    ResponseInfo saveSysUserDept(Long sysUserId, List<Long[]> deptIds, String operater);

}
