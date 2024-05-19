package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.gpt.pojo.command.UploadConfigCommand;
import com.master.chat.gpt.pojo.entity.UploadConfig;
import com.master.chat.gpt.pojo.vo.UploadConfigVO;

import java.util.List;

/**
 * 缩略图配置 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
public interface IUploadConfigService extends IService<UploadConfig> {

    /**
     * 查询缩略图配置分页列表
     *
     * @param query 查询条件
     * @return 缩略图配置集合
     */
    ResponseInfo<IPageInfo<UploadConfigVO>> pageUploadConfig(Query query);

    /**
     * 查询缩略图配置列表
     *
     * @param query 查询条件
     * @return 缩略图配置集合
     */
    ResponseInfo<List<UploadConfigVO>> listUploadConfig(Query query);

    /**
     * 根据主键查询缩略图配置
     *
     * @param id 缩略图配置主键
     * @return 缩略图配置
     */
     ResponseInfo<UploadConfigVO> getUploadConfigById(Long id);

    /**
     * 新增缩略图配置
     *
     * @param command 缩略图配置
     * @return 结果
     */
    ResponseInfo saveUploadConfig(UploadConfigCommand command);

    /**
     * 修改缩略图配置
     *
     * @param command 缩略图配置
     * @return 结果
     */
    ResponseInfo updateUploadConfig(UploadConfigCommand command);

    /**
     * 批量删除缩略图配置
     *
     * @param ids 需要删除的缩略图配置主键集合
     * @return 结果
     */
    ResponseInfo removeUploadConfigByIds(List<Long> ids);

    /**
     * 删除缩略图配置信息
     *
     * @param id 缩略图配置主键
     * @return 结果
     */
    ResponseInfo removeUploadConfigById(Long id);

}
