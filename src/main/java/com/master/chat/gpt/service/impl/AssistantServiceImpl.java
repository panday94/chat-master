package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.AssistantMapper;
import com.master.chat.gpt.pojo.command.AssistantCommand;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.vo.AppAssistantVO;
import com.master.chat.gpt.pojo.vo.AssistantVO;
import com.master.chat.gpt.service.IAssistantService;
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
 * AI助理功能 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
@Service
public class AssistantServiceImpl extends ServiceImpl<AssistantMapper, Assistant> implements IAssistantService {
    @Autowired
    private AssistantMapper assistantMapper;

    /**
     * 根据id获取AI助理功能信息
     *
     * @param id AI助理功能id
     * @return
     */
    private Assistant getAssistant(Long id) {
        Assistant assistant = assistantMapper.selectById(id);
        if (ValidatorUtil.isNull(assistant)) {
            throw new ErrorException("AI助理功能信息不存在，无法操作");
        }
        return assistant;
    }

    @Override
    public ResponseInfo<IPageInfo<AssistantVO>>  pageAssistant(Query query) {
        IPage<AssistantVO> iPage = assistantMapper.pageAssistant(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<AssistantVO>> listAssistant(Query query) {
        return ResponseInfo.success(assistantMapper.listAssistant(query));
    }

    @Override
    public ResponseInfo<List<AppAssistantVO>> listAssistantByApp(Query query) {
        List<AssistantVO> assistants;
        if (ValidatorUtil.isNotNullAndZero(query.getSize()) && ValidatorUtil.isNotNullAndZero(query.getCurrent())) {
            assistants = assistantMapper.pageAssistant(new Page(query.getCurrent(), query.getSize()), query).getRecords();
        } else {
            assistants = assistantMapper.listAssistantRandom(query);
        }
        return ResponseInfo.success(DozerUtil.convertor(assistants, AppAssistantVO.class));
    }

    @Override
    public ResponseInfo<AssistantVO> getAssistantById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getAssistant(id), AssistantVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveAssistant(AssistantCommand command) {
        Assistant assistant = DozerUtil.convertor(command, Assistant.class);
        assistant.setCreateUser(command.getOperater());
        assistantMapper.insert(assistant);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateAssistant(AssistantCommand command) {
        Assistant assistant = getAssistant(command.getId());
        DozerUtil.convertor(command, assistant);
        assistant.setUpdateUser(command.getOperater());
        assistant.setUpdateTime(LocalDateTime.now());
        assistantMapper.updateById(assistant);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeAssistantByIds(List<Long> ids) {
        assistantMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeAssistantById(Long id) {
        assistantMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
