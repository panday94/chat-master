package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据角色id数组获取角色编码列表
     *
     * @param ids 角色id数组
     * @return
     */
    List<Role> listByRoleIds(@Param("ids") List<Long> ids);

}
