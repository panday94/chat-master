package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.AssistantTypeMapper;
import com.master.chat.gpt.pojo.command.AssistantTypeCommand;
import com.master.chat.gpt.pojo.entity.AssistantType;
import com.master.chat.gpt.pojo.vo.AssistantTypeVO;
import com.master.chat.gpt.service.IAssistantTypeService;
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
 *  助手分类 服务实现类
 *
 * @author: Yang
 * @date: 2023-11-22
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class AssistantTypeServiceImpl extends ServiceImpl<AssistantTypeMapper, AssistantType> implements IAssistantTypeService {
    @Autowired
    private AssistantTypeMapper assistantTypeMapper;

    /**
     * 根据id获取助手分类信息
     *
     * @param id 助手分类id
     * @return
     */
    private AssistantType getAssistantType(Long id) {
        AssistantType assistantType = assistantTypeMapper.selectById(id);
        if (ValidatorUtil.isNull(assistantType)) {
            throw new ErrorException("助手分类信息不存在，无法操作");
        }
        return assistantType;
    }

    @Override
    public ResponseInfo<IPageInfo<AssistantTypeVO>> pageAssistantType(Query query) {
        IPage<AssistantTypeVO> iPage = assistantTypeMapper.pageAssistantType(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<AssistantTypeVO>> listAssistantType(Query query) {
        return ResponseInfo.success(assistantTypeMapper.listAssistantType(query));
    }

    @Override
    public ResponseInfo<AssistantTypeVO> getAssistantTypeById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getAssistantType(id), AssistantTypeVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveAssistantType(AssistantTypeCommand command) {
        AssistantType assistantType = DozerUtil.convertor(command, AssistantType.class);
        assistantType.setCreateUser(command.getOperater());
        assistantTypeMapper.insert(assistantType);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateAssistantType(AssistantTypeCommand command) {
        AssistantType assistantType = getAssistantType(command.getId());
        DozerUtil.convertor(command, assistantType);
        assistantType.setUpdateUser(command.getOperater());
        assistantType.setUpdateTime(LocalDateTime.now());
        assistantTypeMapper.updateById(assistantType);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeAssistantTypeByIds(List<Long> ids) {
        assistantTypeMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeAssistantTypeById(Long id) {
        assistantTypeMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
