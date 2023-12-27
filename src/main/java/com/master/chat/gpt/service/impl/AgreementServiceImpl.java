package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.AgreementMapper;
import com.master.chat.gpt.pojo.command.AgreementCommand;
import com.master.chat.gpt.pojo.entity.Agreement;
import com.master.chat.gpt.pojo.vo.AgreementVO;
import com.master.chat.gpt.service.IAgreementService;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.exception.ErrorException;
import com.master.common.utils.DozerUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  内容管理 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements IAgreementService {
    @Autowired
    private AgreementMapper agreementMapper;

    /**
     * 根据id获取内容管理信息
     *
     * @param id 内容管理id
     * @return
     */
    private Agreement getContent(Long id) {
        Agreement agreement = agreementMapper.selectById(id);
        if (ValidatorUtil.isNull(agreement)) {
            throw new ErrorException("内容管理信息不存在，无法操作");
        }
        return agreement;
    }

    @Override
    public ResponseInfo<IPageInfo<AgreementVO>> pageContent(Query query) {
        IPage<AgreementVO> iPage = agreementMapper.pageContent(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<AgreementVO>> listContent(Query query) {
        return ResponseInfo.success(agreementMapper.listContent(query));
    }

    @Override
    public ResponseInfo<AgreementVO> getContentById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getContent(id), AgreementVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveContent(AgreementCommand command) {
        Agreement agreement = DozerUtil.convertor(command, Agreement.class);
        agreement.setCreateUser(command.getOperater());
        agreementMapper.insert(agreement);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateContent(AgreementCommand command) {
        Agreement agreement = getContent(command.getId());
        DozerUtil.convertor(command, agreement);
        agreement.setUpdateUser(command.getOperater());
        agreement.setUpdateTime(LocalDateTime.now());
        agreementMapper.updateById(agreement);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeContentByIds(List<Long> ids) {
        agreementMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeContentById(Long id) {
        agreementMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
