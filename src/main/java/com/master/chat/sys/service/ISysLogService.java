package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.entity.SysLog;
import com.master.chat.sys.pojo.vo.SysLogVO;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;

import java.util.List;

/**
 * 系统日志 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 获取分页系统日志信息
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<SysLogVO>> pageSysLog(Query query);

    /**
     * 获取系统日志列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<SysLogVO>> listSysLog(Query query);

    /**
     * 添加系统日志
     *
     * @param log 系统日志
     * @return
     */
    ResponseInfo saveSysLog(SysLog log);

    /**
     * 删除系统日志
     *
     * @param ids 系统日志id数组
     * @return
     */
    ResponseInfo removeSysLogByIds(List<Long> ids);

    /**
     * 清空系统日志
     *
     * @return
     */
    ResponseInfo clearSysLog();


}
