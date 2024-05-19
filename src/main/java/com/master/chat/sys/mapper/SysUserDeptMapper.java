package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.SysUserDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户部门关联 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * 根据用户id获取用户部门数组
     *
     * @param sysUserId 用户id
     * @return
     */
    List<Long> getSysUserDeptIds(@Param("sysUserId") Long sysUserId);

    /**
     * 清空用户部门信息
     *
     * @param sysUserId 用户id
     */
    void clearSysUserDept(@Param("sysUserId") Long sysUserId);

}
