package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.master.chat.sys.pojo.entity.NoticeRead;
import org.apache.ibatis.annotations.Param;

/**
 * 系统通知已读状态 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface NoticeReadMapper extends BaseMapper<NoticeRead> {

    /**
     * 一键已读系统通知
     *
     * @param sysUserId 系统用户信息
     */
    void allReadNotice(@Param("sysUserId") Long sysUserId);

}
