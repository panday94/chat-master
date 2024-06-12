package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.SysUserPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户岗位关联 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

    /**
     * 根据用户id获取用户部门数组
     *
     * @param sysUserId 用户id
     * @return
     */
    List<Long> getSysUserPostIds(@Param("sysUserId") Long sysUserId);

    /**
     * 清空用户部门信息
     *
     * @param sysUserId 用户id
     */
    void clearSysUserPost(@Param("sysUserId") Long sysUserId);

}
