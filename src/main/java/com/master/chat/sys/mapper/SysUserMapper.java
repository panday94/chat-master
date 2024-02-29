package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.sys.pojo.entity.SysUser;
import com.master.chat.sys.pojo.vo.ContactUserVO;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工账号 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 更新用户登录时间
     *
     * @param id 用户id
     */
    void updateLoginTime(@Param("id") Long id);

    /**
     * 分页查询用户列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<SysUserVO> pageSysUser(IPage<SysUserVO> page, @Param("q") Query query);

    /**
     * 查询用户列表
     *
     * @param query 查询条件
     * @return
     */
    List<SysUserVO> listSysUser(@Param("q") Query query);

    /**
     * 查询用户综合消息列表
     *
     * @param query 查询条件
     * @return
     */
    List<ContactUserVO> listContactSysUser(Query query);
}
