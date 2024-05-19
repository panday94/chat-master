package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.sys.mapper.NoticeMapper;
import com.master.chat.sys.mapper.NoticeReadMapper;
import com.master.chat.sys.pojo.command.NoticeCommand;
import com.master.chat.sys.pojo.entity.Notice;
import com.master.chat.sys.pojo.entity.NoticeRead;
import com.master.chat.sys.pojo.vo.NoticeVO;
import com.master.chat.sys.service.INoticeService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统通知 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeReadMapper noticeReadMapper;

    @Override
    public ResponseInfo<IPageInfo<NoticeVO>> pageNotice(Query query) {
        QueryWrapper<Notice> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("title")), Notice::getTitle, query.get("title"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.getOperater()), Notice::getCreateUser, query.getOperater());
        qw.lambda().like(ValidatorUtil.isNotNull(query.getType()), Notice::getType, query.getType());
        qw.lambda().orderByDesc(BaseEntity::getId);
        IPage<Notice> iPage = noticeMapper.selectPage(new Page<Notice>(query.getCurrent(), query.getSize()), qw);
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), NoticeVO.class));
    }

    @Override
    public ResponseInfo<IPageInfo<NoticeVO>> pageNoticeBySysUser(Query query) {
        IPage<NoticeVO> iPage = noticeMapper.pageNotice(new Page(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<Integer> getUnreadCount(Long sysUserId) {
        return ResponseInfo.success(noticeMapper.getUnreadCount(sysUserId));
    }

    @Override
    public ResponseInfo<NoticeVO> getNoticeById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(noticeMapper.selectById(id), NoticeVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveNotice(NoticeCommand command) {
        Notice notice = DozerUtil.convertor(command, Notice.class);
        notice.setCreateUser(command.getOperater());
        noticeMapper.insert(notice);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateNotice(NoticeCommand command) {
        Notice notice = DozerUtil.convertor(command, Notice.class);
        notice.setUpdateUser(command.getOperater());
        noticeMapper.updateById(notice);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo readNotice(Long id, Long sysUserId) {
        NoticeRead noticeRead = noticeReadMapper.selectOne(new LambdaQueryWrapper<NoticeRead>()
                .eq(NoticeRead::getNoticeId, id).eq(NoticeRead::getSysUserId, sysUserId));
        if (ValidatorUtil.isNotNull(noticeRead)) {
            return ResponseInfo.success();
        }
        noticeRead = NoticeRead.builder().noticeId(id).sysUserId(sysUserId).build();
        noticeReadMapper.insert(noticeRead);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo allReadNotice(Long sysUserId) {
        noticeReadMapper.allReadNotice(sysUserId);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeNoticeByIds(List<Long> ids) {
        noticeMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

}
