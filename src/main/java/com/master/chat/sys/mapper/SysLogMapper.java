package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.SysLog;

/**
 * 系统日志 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 清空系统日志
     */
    void clearSyslog();

}
