package com.master.chat.quartz.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.master.chat.quartz.mapper.SysJobLogMapper;
import com.master.chat.quartz.pojo.command.qry.SysJobQry;
import com.master.chat.quartz.pojo.entity.SysJobLog;
import com.master.chat.quartz.pojo.vo.SysJobLogVO;
import com.master.chat.quartz.service.ISysJobLogService;
import com.master.common.api.IPageInfo;
import com.master.common.api.ResponseInfo;
import com.master.common.utils.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调度日志信息接口 实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService {
    @Autowired
    private SysJobLogMapper jobLogMapper;

    @Override
    public ResponseInfo<IPageInfo<SysJobLogVO>> pageJobLog(SysJobQry query) {
        IPage<SysJobLog> iPage = jobLogMapper.pageJobLog(new Page<SysJobLog>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), SysJobLogVO.class));
    }

    @Override
    public ResponseInfo<List<SysJobLogVO>> listJobLog(SysJobQry query) {
        return ResponseInfo.success(DozerUtil.convertor(jobLogMapper.listJobLog(query), SysJobLogVO.class));
    }

    @Override
    public ResponseInfo<SysJobLogVO> getJobLogById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(jobLogMapper.selectById(id), SysJobLogVO.class));
    }

    @Override
    public ResponseInfo saveJobLog(SysJobLog jobLog) {
        return ResponseInfo.success(jobLogMapper.insert(jobLog));
    }

    @Override
    public ResponseInfo removeJobLogByIds(List<Long> logIds) {
        jobLogMapper.deleteBatchIds(logIds);
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo cleanJobLog() {
        jobLogMapper.cleanJobLog();
        return ResponseInfo.success();
    }

}
