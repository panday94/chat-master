package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.OpenkeyMapper;
import com.master.chat.gpt.pojo.command.OpenkeyCommand;
import com.master.chat.gpt.pojo.entity.Openkey;
import com.master.chat.gpt.pojo.vo.OpenkeyVO;
import com.master.chat.gpt.service.IOpenkeyService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.CommonUtil;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  openai token 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class OpenkeyServiceImpl extends ServiceImpl<OpenkeyMapper, Openkey> implements IOpenkeyService {
    @Autowired
    private OpenkeyMapper openkeyMapper;

    /**
     * 根据id获取openai token信息
     *
     * @param id openai tokenid
     * @return
     */
    private Openkey getOpenkey(Long id) {
        Openkey openkey = openkeyMapper.selectById(id);
        if (ValidatorUtil.isNull(openkey)) {
            throw new ErrorException("openai token信息不存在，无法操作");
        }
        return openkey;
    }

    @Override
    public ResponseInfo<IPageInfo<OpenkeyVO>> pageOpenkey(Query query) {
        IPage<OpenkeyVO> iPage = openkeyMapper.pageOpenkey(new Page<>(query.getCurrent(), query.getSize()), query);
        iPage.getRecords().stream().forEach(v -> {
            v.setAppKey(CommonUtil.passportEncrypt(v.getAppKey()));
            v.setAppSecret(CommonUtil.passportEncrypt(v.getAppSecret()));
        });
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }


    @Override
    public ResponseInfo<List<OpenkeyVO>> listOpenkey(Query query) {
        List<OpenkeyVO> openkeyVOS = openkeyMapper.listOpenkey(query);
        openkeyVOS.stream().forEach(v -> {
            v.setAppKey(CommonUtil.passportEncrypt(v.getAppKey()));
            v.setAppSecret(CommonUtil.passportEncrypt(v.getAppSecret()));
        });
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo<OpenkeyVO> getOpenkeyById(Long id) {
        OpenkeyVO openkeyVO = DozerUtil.convertor(getOpenkey(id), OpenkeyVO.class);
        openkeyVO.setAppKey(CommonUtil.passportEncrypt(openkeyVO.getAppKey()));
        openkeyVO.setAppSecret(CommonUtil.passportEncrypt(openkeyVO.getAppSecret()));
        return ResponseInfo.success(openkeyVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveOpenkey(OpenkeyCommand command) {
        Openkey openkey = DozerUtil.convertor(command, Openkey.class);
        openkey.setCreateUser(command.getOperater());
        openkeyMapper.insert(openkey);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateOpenkey(OpenkeyCommand command) {
        Openkey openkey = getOpenkey(command.getId());
        openkey.setModel(command.getModel());
        openkey.setAppId(command.getAppId());
        command.setAppKey(ValidatorUtil.isNotNull(command.getAppKey()) && !command.getAppKey().contains(StringPoolConstant.STAR) ? command.getAppKey() : openkey.getAppKey());
        command.setAppSecret(ValidatorUtil.isNotNull(command.getAppSecret()) && !command.getAppSecret().contains(StringPoolConstant.STAR) ? command.getAppSecret() : openkey.getAppSecret());
        openkey.setTotalTokens(command.getTotalTokens());
        openkey.setRemark(command.getRemark());
        openkey.setStatus(command.getStatus());
        openkey.setUpdateUser(command.getOperater());
        openkey.setUpdateTime(LocalDateTime.now());
        openkeyMapper.updateById(openkey);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeOpenkeyByIds(List<Long> ids) {
        openkeyMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeOpenkeyById(Long id) {
        openkeyMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
