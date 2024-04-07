package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据角色获取菜单资源信息
     *
     * @param roleIds 角色id数组
     * @param types   资源信息
     * @return 资源列表
     */
    List<Resource> listResource(@Param("roleIds") List<Long> roleIds, @Param("types") List<Integer> types);

}
