package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.entity.LoginLog;
import com.master.chat.sys.pojo.vo.LoginLogVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 登录日志 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ILoginLogService extends IService<LoginLog> {

    /**
     * 获取登录日志分页信息
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<LoginLogVO>> pageLoginLog(Query query);


    /**
     * 获取登录日志列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<LoginLogVO>> listLoginLog(Query query);

    /**
     * 添加登录日志
     *
     * @param log 登录日志信息
     * @return
     */
    ResponseInfo saveLoginLog(LoginLog log);

    /**
     * 添加登录日志
     *
     * @param sysUserId     用户id
     * @param sessionId     会话标识
     * @param username      用户名
     * @param status        状态
     * @param authorization 身份标识
     * @param msg           登录信息
     * @return
     */
    ResponseInfo saveLoginLog(Long sysUserId, String sessionId, String username, Integer status, String authorization, String msg);

    /**
     * 根据id批量删除某条登录日志
     *
     * @param ids 日志id数组
     * @return
     */
    ResponseInfo removeLoginLogByIds(List<Long> ids);

    /**
     * 清空登录日志
     *
     * @return
     */
    ResponseInfo cleanLoginLog();

    /**
     * 强退用户
     *
     * @param ids 登录信息id
     * @return
     */
    ResponseInfo forceLogout(List<Long> ids);

    /**
     * 禁用登录信息
     *
     * @param sysUserId 用户id
     * @return
     */
    ResponseInfo disableLogin(Long sysUserId);

    /**
     * 禁用登录信息
     *
     * @param sysUserId 用户id
     * @param sessionId 当前登录会话标识
     * @return
     */
    ResponseInfo disableLogin(Long sysUserId, String sessionId);

}
