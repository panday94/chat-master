package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.AuditLogMapper;
import com.master.chat.sys.pojo.command.AuditCommand;
import com.master.chat.sys.pojo.entity.AuditLog;
import com.master.chat.sys.pojo.vo.AuditLogVO;
import com.master.chat.sys.service.IAuditLogService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 审核日志 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements IAuditLogService {
    @Autowired
    private AuditLogMapper logMapper;

    @Override
    public ResponseInfo<IPageInfo<AuditLogVO>> pageAuditLog(Long id, Long current, Long size, Integer type) {
        QueryWrapper<AuditLog> qw = new QueryWrapper<>();
        qw.lambda().eq(ValidatorUtil.isNotNull(type), AuditLog::getType, type).orderByDesc(AuditLog::getId);
        IPage<AuditLog> iPage = logMapper.selectPage(new Page<AuditLog>(current, size), qw);
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), AuditLogVO.class));
    }

    @Override
    public ResponseInfo<AuditLogVO> getAuditLog(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(logMapper.selectById(id), AuditLogVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveAuditLog(AuditCommand command) {
        logMapper.insert(AuditCommand.transferToEntity(command));
        return ResponseInfo.success();
    }

}
