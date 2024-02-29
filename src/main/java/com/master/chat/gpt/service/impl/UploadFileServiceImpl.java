package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.UploadFileMapper;
import com.master.chat.gpt.pojo.command.UploadFileCommand;
import com.master.chat.gpt.pojo.entity.UploadFile;
import com.master.chat.gpt.pojo.vo.UploadFileVO;
import com.master.chat.gpt.service.IUploadFileService;
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
 *  文件管理 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile> implements IUploadFileService {
    @Autowired
    private UploadFileMapper uploadFileMapper;

    /**
     * 根据id获取文件管理信息
     *
     * @param id 文件管理id
     * @return
     */
    private UploadFile getFile(Long id) {
        UploadFile uploadFile = uploadFileMapper.selectById(id);
        if (ValidatorUtil.isNull(uploadFile)) {
            throw new ErrorException("文件管理信息不存在，无法操作");
        }
        return uploadFile;
    }

    @Override
    public ResponseInfo<IPageInfo<UploadFileVO>> pageFile(Query query) {
        IPage<UploadFileVO> iPage = uploadFileMapper.pageFile(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<UploadFileVO>> listFile(Query query) {
        return ResponseInfo.success(uploadFileMapper.listFile(query));
    }

    @Override
    public ResponseInfo<UploadFileVO> getFileById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getFile(id), UploadFileVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveFile(UploadFileCommand command) {
        UploadFile uploadFile = DozerUtil.convertor(command, UploadFile.class);
        uploadFile.setCreateUser(command.getOperater());
        uploadFileMapper.insert(uploadFile);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateFile(UploadFileCommand command) {
        UploadFile uploadFile = getFile(command.getId());
        DozerUtil.convertor(command, uploadFile);
        uploadFile.setUpdateUser(command.getOperater());
        uploadFile.setUpdateTime(LocalDateTime.now());
        uploadFileMapper.updateById(uploadFile);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeFileByIds(List<Long> ids) {
        uploadFileMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeFileById(Long id) {
        uploadFileMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
