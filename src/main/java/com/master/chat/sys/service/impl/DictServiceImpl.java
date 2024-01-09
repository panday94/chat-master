package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.common.constant.RedisConstants;
import com.master.chat.common.util.RedisUtils;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.sys.mapper.DictMapper;
import com.master.chat.sys.pojo.command.DictCommand;
import com.master.chat.sys.pojo.entity.Dict;
import com.master.chat.sys.pojo.vo.DictVO;
import com.master.chat.sys.service.IDictService;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.StringPoolConstant;
import com.master.common.enums.StatusEnum;
import com.master.common.exception.ErrorException;
import com.master.common.utils.DozerUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字典 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private RedisUtils redisUtil;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init() {
        loadingDictCache();
    }

    /**
     * 获取字典
     *
     * @param id 字典id
     * @return 字典信息
     */
    private Dict getDict(Long id) {
        Dict dict = dictMapper.selectById(id);
        if (ValidatorUtil.isNull(dict)) {
            throw new ErrorException("字典信息获取失败，无法操作");
        }
        return dict;
    }

    /**
     * 获取指定类型字典
     *
     * @param type 类型
     * @return
     */
    private List<Dict> listDictByType(String type) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dict::getDictType, type);
        return dictMapper.selectList(new LambdaQueryWrapper<Dict>().eq(Dict::getDictType, type));
    }

    @Override
    public ResponseInfo<IPageInfo<DictVO>> pageDict(Query query) {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("label")), Dict::getLabel, query.get("label"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("dictType")), Dict::getDictType, query.get("dictType"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), Dict::getStatus, query.getStatus());
        qw.lambda().orderByDesc(BaseEntity::getId);
        IPage<Dict> iPage = dictMapper.selectPage(new Page<Dict>(query.getCurrent(), query.getSize()), qw);
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), DictVO.class));
    }

    @Override
    public ResponseInfo<List<DictVO>> listDict(String type) {
        return ResponseInfo.success(DozerUtil.convertor(listDictByType(type), DictVO.class));
    }

    @Override
    public ResponseInfo<DictVO> getDictById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getDict(id), DictVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveDict(DictCommand command) {
        Dict dict = DozerUtil.convertor(command, Dict.class);
        dict.setCreateUser(command.getOperater());
        dictMapper.insert(dict);
        return ResponseInfo.success(dict.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateDict(DictCommand command) {
        Dict dict = getDict(command.getId());
        dict.setUpdateUser(command.getOperater());
        DozerUtil.convertor(command, dict);
        dictMapper.updateById(dict);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateDictStatus(DictCommand command) {
        Dict dict = getDict(command.getId());
        dict.setUpdateUser(command.getOperater());
        if (ValidatorUtil.isNull(command.getStatus())) {
            dict.setStatus(StatusEnum.ENABLED.getValue().equals(dict.getStatus()) ? StatusEnum.DISABLED.getValue() : StatusEnum.ENABLED.getValue());
        } else {
            dict.setStatus(command.getStatus());
        }
        dictMapper.updateById(dict);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeDictByIds(List<Long> ids) {
        dictMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    /**
     * 清空字典缓存
     */
    public void clearDictCache() {
        Set<String> keys = redisUtil.getKeys(RedisConstants.SYS_DICT_KEY + StringPoolConstant.ASTERISK);
        redisUtil.del(keys);
    }

    /**
     * 加载字典缓存数据
     */
    public void loadingDictCache() {
        Map<String, List<Dict>> listMap = dictMapper.selectList(null).stream().collect(Collectors.groupingBy(Dict::getDictType));
        for (Map.Entry<String, List<Dict>> entry : listMap.entrySet()) {
            redisUtil.set(RedisConstants.SYS_DICT_KEY + entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(Dict::getSort)).collect(Collectors.toList()));
        }
    }

    @Override
    public ResponseInfo refreshDictCache() {
        clearDictCache();
        loadingDictCache();
        return ResponseInfo.success();
    }

}
