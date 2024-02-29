package com.master.chat.sys.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.comm.constant.RedisConstants;
import com.master.chat.comm.constant.SysConfigConstants;
import com.master.chat.comm.util.RedisUtils;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.sys.mapper.SysConfigMapper;
import com.master.chat.sys.pojo.command.SysConfigCommand;
import com.master.chat.sys.pojo.entity.SysConfig;
import com.master.chat.sys.pojo.vo.SysConfigVO;
import com.master.chat.sys.service.ISysConfigService;
import com.master.chat.common.annotation.DataSource;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.DataSourceTypeEnum;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 系统配置 接口实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    @Autowired
    private SysConfigMapper configMapper;
    @Autowired
    private RedisUtils redisUtil;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    private SysConfig getSysConfig(Long id) {
        SysConfig sysConfig = configMapper.selectById(id);
        if (ValidatorUtil.isNull(sysConfig)) {
            throw new ValidateException();
        }
        return sysConfig;
    }

    private QueryWrapper<SysConfig> getQw(Query query) {
        QueryWrapper<SysConfig> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), SysConfig::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("configKey")), SysConfig::getConfigKey, query.get("configKey"));
        qw.lambda().eq(ValidatorUtil.isNotNull(query.getType()), SysConfig::getType, query.getType());
        qw.lambda().ge(ValidatorUtil.isNotNull(query.getStartDate()), SysConfig::getCreateTime, query.getStartDate());
        qw.lambda().le(ValidatorUtil.isNotNull(query.getEndDate()), SysConfig::getCreateTime, query.getEndDate());
        qw.lambda().orderByDesc(BaseEntity::getId);
        return qw;
    }

    @Override
    @DataSource(DataSourceTypeEnum.MASTER)
    public ResponseInfo<IPageInfo<SysConfigVO>> pageConfig(Query query) {
        IPage<SysConfig> iPage = configMapper.selectPage(new Page<SysConfig>(query.getCurrent(), query.getSize()), getQw(query));
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), SysConfigVO.class));
    }

    @Override
    public ResponseInfo<List<SysConfigVO>> listConfig(Query query) {
        List<SysConfig> list = configMapper.selectList(getQw(query));
        return ResponseInfo.success(DozerUtil.convertor(list, SysConfigVO.class));
    }

    @Override
    public ResponseInfo<SysConfigVO> getConfigById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getSysConfig(id), SysConfigVO.class));
    }

    @Override
    public ResponseInfo<String> getConfigByKey(String configKey) {
        String configValue = redisUtil.get(getCacheKey(configKey));
        if (ValidatorUtil.isNotNull(configValue)) {
            return ResponseInfo.success(configValue);
        }
        QueryWrapper<SysConfig> qw = new QueryWrapper<>();
        qw.lambda().eq(SysConfig::getConfigKey, configKey);
        SysConfig retConfig = configMapper.selectOne(qw);
        if (ValidatorUtil.isNotNull(retConfig)) {
            redisUtil.set(getCacheKey(configKey), retConfig.getConfigValue());
            return ResponseInfo.success(retConfig.getConfigValue());
        }
        return ResponseInfo.success(StringPoolConstant.EMPTY);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveConfig(SysConfigCommand command) {
        SysConfig sysConfig = DozerUtil.convertor(command, SysConfig.class);
        sysConfig.setCreateUser(command.getOperater());
        configMapper.insert(sysConfig);
        redisUtil.set(getCacheKey(command.getConfigKey()), command.getConfigValue());
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateConfig(SysConfigCommand command) {
        SysConfig sysConfig = getSysConfig(command.getId());
        sysConfig.setUpdateUser(command.getOperater());
        DozerUtil.convertor(command, sysConfig);
        configMapper.updateById(sysConfig);
        redisUtil.set(getCacheKey(command.getConfigKey()), command.getConfigValue());
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeConfigByIds(List<Long> ids) {
        for (Long id : ids) {
            SysConfig config = getSysConfig(id);
            if (StatusEnum.ENABLED.getValue().equals(config.getType())) {
                throw new BusinessException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            configMapper.deleteById(id);
            redisUtil.del(getCacheKey(config.getConfigKey()));
        }
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
        return ResponseInfo.success();
    }

    @Override
    public Boolean checkConfigKeyUnique(SysConfigCommand command) {
        Long configId = ValidatorUtil.isNull(command.getId()) ? -1L : command.getId();
        QueryWrapper<SysConfig> qw = new QueryWrapper<>();
        qw.lambda().eq(SysConfig::getConfigKey, command.getConfigKey()).last("limit 1");
        SysConfig info = configMapper.selectOne(qw);
        if (ValidatorUtil.isNotNull(info) && info.getId().longValue() != configId.longValue()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean getKeyValue(String key) {
        String captchaOnOff = getConfigByKey(key).getData();
        if (ValidatorUtil.isNull(captchaOnOff)) {
            return true;
        }
        return Convert.toBool(captchaOnOff);
    }

    @Override
    public Boolean validateCaptcha(String code, String uuid) {
        Boolean flag = getKeyValue(SysConfigConstants.CAPTCHA_ON_OFF);
        if (!flag) {
            return true;
        }
        String verifyKey = RedisConstants.CAPTCHA_CODE_KEY + Optional.ofNullable(uuid).orElse(StringPoolConstant.EMPTY);
        String captcha = redisUtil.get(verifyKey);
        if (ValidatorUtil.isNull(captcha)) {
            throw new ErrorException("验证码已过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new ErrorException("验证码错误");
        }
        redisUtil.del(verifyKey);
        return true;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return RedisConstants.SYS_CONFIG_KEY + configKey;
    }

    /**
     * 清空参数缓存数据
     */
    private void clearConfigCache() {
        Set<String> keys = redisUtil.getKeys(RedisConstants.SYS_CONFIG_KEY + StringPoolConstant.ASTERISK);
        redisUtil.del(keys);
    }

    /**
     * 加载参数缓存数据
     */
    private void loadingConfigCache() {
        List<SysConfig> configsList = configMapper.selectList(null);
        for (SysConfig config : configsList) {
            redisUtil.set(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

}
