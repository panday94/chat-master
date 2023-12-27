package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.NoticeReadMapper;
import com.master.chat.sys.service.INoticeReadService;
import com.master.chat.sys.pojo.entity.NoticeRead;
import org.springframework.stereotype.Service;

/**
 *  系统通知已读状态 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class NoticeReadServiceImpl extends ServiceImpl<NoticeReadMapper, NoticeRead> implements INoticeReadService {

}
