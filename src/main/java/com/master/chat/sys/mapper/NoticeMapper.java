package com.master.chat.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.master.chat.sys.pojo.entity.Notice;
import com.master.chat.sys.pojo.vo.NoticeVO;
import com.master.common.api.Query;
import org.apache.ibatis.annotations.Param;

/**
 * 系统通知 Mapper 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 获取系统通知分页数据
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return
     */
    IPage<NoticeVO> pageNotice(Page page, @Param("q") Query query);

    /**
     * 根据登录账号获取系统未读数量
     *
     * @param sysUserId 系统账号id
     * @return
     */
    Integer getUnreadCount(@Param("sysUserId") Long sysUserId);

}
