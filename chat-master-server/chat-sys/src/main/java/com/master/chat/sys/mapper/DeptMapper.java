package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.Dept;
import com.master.chat.sys.pojo.vo.DeptVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门职位 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 根据账号获取部门信息
     *
     * @param sysUserId 账号id
     * @return
     */
    List<DeptVO> listDeptBySysUserId(@Param("sysUserId") Long sysUserId);

    /**
     * 根据当前部门id获取子分类信息
     *
     * @param ids 部门id
     * @return
     */
    List<DeptVO> listDeptById(@Param("id") Long id);

    /**
     * 根据当前部门id获取子分类信息
     *
     * @param ids 部门id
     * @return
     */
    List<DeptVO> listDeptByIds(@Param("ids") List<Long> ids);

}
