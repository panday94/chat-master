package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.UploadConfigMapper;
import com.master.chat.gpt.pojo.command.UploadConfigCommand;
import com.master.chat.gpt.pojo.entity.UploadConfig;
import com.master.chat.gpt.pojo.vo.UploadConfigVO;
import com.master.chat.gpt.service.IUploadConfigService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  缩略图配置 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class UploadConfigServiceImpl extends ServiceImpl<UploadConfigMapper, UploadConfig> implements IUploadConfigService {
    @Autowired
    private UploadConfigMapper uploadConfigMapper;

    /**
     * 根据id获取缩略图配置信息
     *
     * @param id 缩略图配置id
     * @return
     */
    private UploadConfig getUploadConfig(Long id) {
        UploadConfig uploadConfig = uploadConfigMapper.selectById(id);
        if (ValidatorUtil.isNull(uploadConfig)) {
            throw new ErrorException("缩略图配置信息不存在，无法操作");
        }
        return uploadConfig;
    }

    @Override
    public ResponseInfo<IPageInfo<UploadConfigVO>> pageUploadConfig(Query query) {
        IPage<UploadConfigVO> iPage = uploadConfigMapper.pageUploadConfig(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<UploadConfigVO>> listUploadConfig(Query query) {
        return ResponseInfo.success(uploadConfigMapper.listUploadConfig(query));
    }

    @Override
    public ResponseInfo<UploadConfigVO> getUploadConfigById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getUploadConfig(id), UploadConfigVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveUploadConfig(UploadConfigCommand command) {
        UploadConfig uploadConfig = DozerUtil.convertor(command, UploadConfig.class);
        uploadConfig.setCreateUser(command.getOperater());
        uploadConfigMapper.insert(uploadConfig);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateUploadConfig(UploadConfigCommand command) {
        UploadConfig uploadConfig = getUploadConfig(command.getId());
        DozerUtil.convertor(command, uploadConfig);
        uploadConfig.setUpdateUser(command.getOperater());
        uploadConfig.setUpdateTime(LocalDateTime.now());
        uploadConfigMapper.updateById(uploadConfig);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeUploadConfigByIds(List<Long> ids) {
        uploadConfigMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeUploadConfigById(Long id) {
        uploadConfigMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
