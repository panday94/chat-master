package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.ModelMapper;
import com.master.chat.gpt.pojo.command.ModelCommand;
import com.master.chat.gpt.pojo.entity.Model;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IModelService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  大模型信息 服务实现类
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements IModelService {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 根据id获取大模型信息信息
     *
     * @param id 大模型信息id
     * @return
     */
    private Model getModel(Long id) {
        Model model = modelMapper.selectById(id);
        if (ValidatorUtil.isNull(model)) {
            throw new ErrorException("大模型信息信息不存在，无法操作");
        }
        return model;
    }

    @Override
    public ResponseInfo<IPageInfo<ModelVO>> pageModel(Query query) {
        IPage<ModelVO> iPage = modelMapper.pageModel(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<ModelVO>> listModel(Query query) {
        return ResponseInfo.success(modelMapper.listModel(query));
    }

    @Override
    public ResponseInfo<ModelVO> getModelById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getModel(id), ModelVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveModel(ModelCommand command) {
        Model model = DozerUtil.convertor(command, Model.class);
        model.setCreateUser(command.getOperater());
        modelMapper.insert(model);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateModel(ModelCommand command) {
        Model model = getModel(command.getId());
        DozerUtil.convertor(command, model);
        model.setUpdateUser(command.getOperater());
        model.setUpdateTime(LocalDateTime.now());
        modelMapper.updateById(model);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeModelByIds(List<Long> ids) {
        modelMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeModelById(Long id) {
        modelMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
