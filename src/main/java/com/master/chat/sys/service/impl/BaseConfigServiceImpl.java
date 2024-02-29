package com.master.chat.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.BaseConfigMapper;
import com.master.chat.sys.pojo.command.BaseConfigCommand;
import com.master.chat.sys.pojo.entity.BaseConfig;
import com.master.chat.sys.pojo.vo.BaseConfigVO;
import com.master.chat.sys.service.IBaseConfigService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DateUtil;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础配置 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@Service
public class BaseConfigServiceImpl extends ServiceImpl<BaseConfigMapper, BaseConfig> implements IBaseConfigService {
    @Autowired
    private BaseConfigMapper baseConfigMapper;

    /**
     * 根据id获取基础配置信息
     *
     * @param id 基础配置id
     * @return
     */
    private BaseConfig getBaseConfig(Long id) {
        BaseConfig baseConfig = baseConfigMapper.selectById(id);
        if (ValidatorUtil.isNull(baseConfig)) {
            throw new ErrorException("基础配置信息不存在，无法操作");
        }
        return baseConfig;
    }

    @Override
    public ResponseInfo<IPageInfo<BaseConfigVO>> pageBaseConfig(Query query) {
        IPage<BaseConfigVO> iPage = baseConfigMapper.pageBaseConfig(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<BaseConfigVO>> listBaseConfig(Query query) {
        return ResponseInfo.success(baseConfigMapper.listBaseConfig(query));
    }

    @Override
    public ResponseInfo<BaseConfigVO> getBaseConfigById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getBaseConfig(id), BaseConfigVO.class));
    }

    @Override
    public <T> T getBaseConfigByName(String name, Class<T> t) {
        BaseConfig baseConfig = baseConfigMapper.selectOne(new LambdaQueryWrapper<BaseConfig>().eq(BaseConfig::getName, name));
        if (ValidatorUtil.isNull(baseConfig) || ValidatorUtil.isNull(baseConfig.getData())) {
            return null;
        }
        return JSON.parseObject(baseConfig.getData(), t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo test(String name) {
        log.info("开始打印：{}，时间：{}", Thread.currentThread().getName(), DateUtil.getCurrentTime());
        BaseConfig baseConfig = BaseConfig.builder().name("测试").build();
        baseConfig.setName("测试dw");
        baseConfigMapper.insert(baseConfig);
        try {
            log.info("进入休眠：{}，时间：{}", Thread.currentThread().getName(), DateUtil.getCurrentTime());
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        baseConfig = baseConfigMapper.selectById(baseConfig.getId());
        log.info("查询结果：{}，时间：{}", baseConfig.getName(), DateUtil.getCurrentTime());
        log.info("结束打印：{}，时间：{}", Thread.currentThread().getName(), DateUtil.getCurrentTime());
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveBaseConfig(BaseConfigCommand command) {
        BaseConfig baseConfig = baseConfigMapper.selectOne(new LambdaQueryWrapper<BaseConfig>().eq(BaseConfig::getName, command.getName()));
        if (ValidatorUtil.isNotNull(baseConfig)) {
            return updateBaseConfig(command);
        }
        baseConfig = DozerUtil.convertor(command, BaseConfig.class);
        baseConfig.setCreateUser(command.getOperater());
        baseConfigMapper.insert(baseConfig);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateBaseConfig(BaseConfigCommand command) {
        BaseConfig baseConfig = baseConfigMapper.selectOne(new LambdaQueryWrapper<BaseConfig>().eq(BaseConfig::getName, command.getName()));
        baseConfig.setUpdateUser(command.getOperater());
        baseConfig.setUpdateTime(LocalDateTime.now());
        baseConfig.setData(command.getData());
        baseConfigMapper.updateById(baseConfig);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeBaseConfigByIds(List<Long> ids) {
        baseConfigMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeBaseConfigById(Long id) {
        baseConfigMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
