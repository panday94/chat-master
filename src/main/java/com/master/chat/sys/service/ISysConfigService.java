package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.SysConfigCommand;
import com.master.chat.sys.pojo.entity.SysConfig;
import com.master.chat.sys.pojo.vo.SysConfigVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 系统配置 接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 查询参数配置列表
     *
     * @param query 查询条件
     * @return 参数配置集合
     */
    ResponseInfo<IPageInfo<SysConfigVO>> pageConfig(Query query);

    /**
     * 查询参数配置列表
     *
     * @param query 查询条件
     * @return 参数配置集合
     */
    ResponseInfo<List<SysConfigVO>> listConfig(Query query);

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    ResponseInfo<SysConfigVO> getConfigById(Long configId);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    ResponseInfo<String> getConfigByKey(String configKey);

    /**
     * 新增参数配置
     *
     * @param command 参数配置信息
     * @return 结果
     */
    ResponseInfo saveConfig(SysConfigCommand command);

    /**
     * 修改参数配置
     *
     * @param command 参数配置信息
     * @return 结果
     */
    ResponseInfo updateConfig(SysConfigCommand command);

    /**
     * 批量删除参数信息
     *
     * @param ids 需要删除的参数ID
     * @return
     */
    ResponseInfo removeConfigByIds(List<Long> ids);

    /**
     * 重置参数缓存数据
     *
     * @return
     */
    ResponseInfo resetConfigCache();

    /**
     * 校验参数键名是否唯一
     *
     * @param command 参数信息
     * @return 结果
     */
    Boolean checkConfigKeyUnique(SysConfigCommand command);

    /**
     * 获取验证码开关
     *
     * @param key 键名
     * @return true开启，false关闭
     */
    Boolean getKeyValue(String key);

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 会话标识
     * @return true开启，false关闭
     */
    Boolean validateCaptcha(String code, String uuid);

}
