package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.SysLogMapper;
import com.master.chat.sys.service.ISysLogService;
import com.master.chat.sys.pojo.entity.SysLog;
import com.master.chat.sys.pojo.vo.SysLogVO;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.utils.DozerUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统日志 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 获取筛选条件
     *
     * @param query
     * @return
     */
    private QueryWrapper<SysLog> getQw(Query query) {
        QueryWrapper<SysLog> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("title")), SysLog::getTitle, query.get("title"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("username")), SysLog::getUsername, query.get("username"));
        qw.lambda().eq(ValidatorUtil.isNotNull(query.get("businessType")), SysLog::getBusinessType, query.get("businessType"));
        qw.lambda().ge(ValidatorUtil.isNotNull(query.getStartDate()), SysLog::getCreateTime, query.getStartDate());
        qw.lambda().le(ValidatorUtil.isNotNull(query.getEndDate()), SysLog::getCreateTime, query.getEndDate());
        qw.lambda().orderByDesc(SysLog::getId);
        return qw;
    }

    @Override
    public ResponseInfo<IPageInfo<SysLogVO>> pageSysLog(Query query) {
        IPage<SysLog> iPage = sysLogMapper.selectPage(new Page<SysLog>(query.getCurrent(), query.getSize()), getQw(query));
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), SysLogVO.class));
    }

    @Override
    public ResponseInfo<List<SysLogVO>> listSysLog(Query query) {
        return ResponseInfo.success(DozerUtil.convertor(sysLogMapper.selectList(getQw(query)), SysLogVO.class));
    }

    @Override
    public ResponseInfo saveSysLog(SysLog log) {
        sysLogMapper.insert(log);
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo removeSysLogByIds(List<Long> ids) {
        sysLogMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo clearSysLog() {
        sysLogMapper.clearSyslog();
        return ResponseInfo.success();
    }

}
