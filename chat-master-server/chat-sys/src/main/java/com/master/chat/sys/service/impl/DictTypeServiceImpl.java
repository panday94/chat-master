package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.sys.mapper.DictTypeMapper;
import com.master.chat.sys.pojo.command.DictTypeCommand;
import com.master.chat.sys.pojo.entity.DictType;
import com.master.chat.sys.pojo.vo.DictTypeVO;
import com.master.chat.sys.service.IDictTypeService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典类型 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {
    @Autowired
    private DictTypeMapper dictTypeMapper;

    /**
     * 获取字典类型
     *
     * @param id 字典类型id
     * @return 字典类型信息
     */
    private DictType getDictType(Long id) {
        DictType dictType = dictTypeMapper.selectById(id);
        if (ValidatorUtil.isNull(dictType)) {
            throw new ErrorException("字典类型信息获取失败，无法操作");
        }
        return dictType;
    }

    /**
     * 获取mybatis-plus筛选条件
     *
     * @param query 筛选条件
     * @return
     */
    private QueryWrapper<DictType> getQw(Query query) {
        QueryWrapper<DictType> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), DictType::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("dictType")), DictType::getType, query.get("dictType"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), DictType::getStatus, query.getStatus());
        qw.lambda().ge(ValidatorUtil.isNotNull(query.getStartDate()), DictType::getCreateTime, query.getStartDate());
        qw.lambda().le(ValidatorUtil.isNotNull(query.getEndDate()), DictType::getCreateTime, query.getEndDate());
        qw.lambda().orderByDesc(BaseEntity::getId);
        return qw;
    }

    @Override
    public ResponseInfo<IPageInfo<DictTypeVO>> pageDictType(Query query) {
        IPage<DictType> iPage = dictTypeMapper.selectPage(new Page<DictType>(query.getCurrent(), query.getSize()), getQw(query));
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), DictTypeVO.class));
    }

    @Override
    public ResponseInfo<List<DictTypeVO>> listDictType(Query query) {
        return ResponseInfo.success(DozerUtil.convertor(dictTypeMapper.selectList(getQw(query)), DictTypeVO.class));
    }

    @Override
    public ResponseInfo<DictTypeVO> getDictTypeById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getDictType(id), DictTypeVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveDictType(DictTypeCommand command) {
        DictType dictType = DozerUtil.convertor(command, DictType.class);
        dictType.setCreateUser(command.getOperater());
        dictTypeMapper.insert(dictType);
        return ResponseInfo.success(dictType.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateDictType(DictTypeCommand command) {
        DictType dictType = getDictType(command.getId());
        dictType.setUpdateUser(command.getOperater());
        DozerUtil.convertor(command, dictType);
        dictTypeMapper.updateById(dictType);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateDictTypeStatus(DictTypeCommand command) {
        DictType dictType = getDictType(command.getId());
        dictType.setUpdateUser(command.getOperater());
        if (ValidatorUtil.isNull(command.getStatus())) {
            dictType.setStatus(StatusEnum.ENABLED.getValue().equals(dictType.getStatus()) ? StatusEnum.DISABLED.getValue() : StatusEnum.ENABLED.getValue());
        } else {
            dictType.setStatus(command.getStatus());
        }
        dictTypeMapper.updateById(dictType);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeDictTypeByIds(List<Long> ids) {
        dictTypeMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

}
