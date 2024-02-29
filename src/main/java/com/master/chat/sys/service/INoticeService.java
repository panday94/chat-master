package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.NoticeCommand;
import com.master.chat.sys.pojo.entity.Notice;
import com.master.chat.sys.pojo.vo.NoticeVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 系统通知 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 获取通知分页列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<NoticeVO>> pageNotice(Query query);

    /**
     * 根据登录账号获取系统通知数据
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<NoticeVO>> pageNoticeBySysUser(Query query);

    /**
     * 根据登录账号获取系统未读数量
     *
     * @param sysUserId 系统用户id
     * @return
     */
    ResponseInfo<Integer> getUnreadCount(Long sysUserId);

    /**
     * 根据id获取通知详情
     *
     * @param id 通知id
     * @return
     */
    ResponseInfo<NoticeVO> getNoticeById(Long id);

    /**
     * 添加通知
     *
     * @param command 通知内容
     * @return
     */
    ResponseInfo saveNotice(NoticeCommand command);

    /**
     * 修改通知
     *
     * @param command 通知内容
     * @return
     */
    ResponseInfo updateNotice(NoticeCommand command);

    /**
     * 已读通知
     *
     * @param id        通知id
     * @param sysUserId 系统用户id
     * @return
     */
    ResponseInfo readNotice(Long id, Long sysUserId);

    /**
     * 一键已读通知
     *
     * @param sysUserId 系统用户id
     * @return
     */
    ResponseInfo allReadNotice(Long sysUserId);

    /**
     * 删除通知
     *
     * @param ids 通知id数组
     * @return
     */
    ResponseInfo removeNoticeByIds(List<Long> ids);

}
