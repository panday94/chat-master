package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.LoginLog;
import org.apache.ibatis.annotations.Param;

/**
 * 登录日志 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 禁用登录信息
     *
     * @param sysUserId 用户id
     */
    void disableLogin(@Param("sysUserId") Long sysUserId);

    /**
     * 禁用登录信息
     *
     * @param sysUserId 用户id
     * @param sessionId 当前会话标识
     */
    void disableLoginBySession(@Param("sysUserId") Long sysUserId, @Param("sessionId") String sessionId);

    /**
     * 禁用登录信息
     *
     * @param id 登录id
     */
    void disableLoginById(@Param("id") Long id);

    /**
     * 清空登录日志
     */
    void cleanLoginLog();

}
