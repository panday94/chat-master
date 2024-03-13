package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.CombMapper;
import com.master.chat.gpt.pojo.command.CombCommand;
import com.master.chat.gpt.pojo.entity.Comb;
import com.master.chat.gpt.pojo.vo.CombVO;
import com.master.chat.gpt.service.ICombService;
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
 *  会员套餐 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class CombServiceImpl extends ServiceImpl<CombMapper, Comb> implements ICombService {
    @Autowired
    private CombMapper combMapper;

    /**
     * 根据id获取会员套餐信息
     *
     * @param id 会员套餐id
     * @return
     */
    private Comb getComb(Long id) {
        Comb comb = combMapper.selectById(id);
        if (ValidatorUtil.isNull(comb)) {
            throw new ErrorException("会员套餐信息不存在，无法操作");
        }
        return comb;
    }

    @Override
    public ResponseInfo<IPageInfo<CombVO>> pageComb(Query query) {
        IPage<CombVO> iPage = combMapper.pageComb(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<CombVO>> listComb(Query query) {
        return ResponseInfo.success(combMapper.listComb(query));
    }

    @Override
    public ResponseInfo<CombVO> getCombById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getComb(id), CombVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveComb(CombCommand command) {
        Comb comb = DozerUtil.convertor(command, Comb.class);
        comb.setCreateUser(command.getOperater());
        combMapper.insert(comb);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateComb(CombCommand command) {
        Comb comb = getComb(command.getId());
        DozerUtil.convertor(command, comb);
        comb.setUpdateUser(command.getOperater());
        comb.setUpdateTime(LocalDateTime.now());
        combMapper.updateById(comb);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeCombByIds(List<Long> ids) {
        combMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeCombById(Long id) {
        combMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
