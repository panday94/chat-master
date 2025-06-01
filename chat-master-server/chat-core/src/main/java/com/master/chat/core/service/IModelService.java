package com.master.chat.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.core.pojo.command.ModelCommand;
import com.master.chat.core.pojo.entity.Model;
import com.master.chat.core.pojo.vo.ModelVO;

import java.util.List;

/**
 * 大模型信息 服务类
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public interface IModelService extends IService<Model> {

    /**
     * 查询大模型信息分页列表
     *
     * @param query 查询条件
     * @return 大模型信息集合
     */
    ResponseInfo<IPageInfo<ModelVO>> pageModel(Query query);

    /**
     * 查询大模型信息列表
     *
     * @param query 查询条件
     * @return 大模型信息集合
     */
    ResponseInfo<List<ModelVO>> listModel(Query query);

    /**
     * 根据主键查询大模型信息
     *
     * @param id 大模型信息主键
     * @return 大模型信息
     */
    ResponseInfo<ModelVO> getModelById(Long id);

    /**
     * 新增大模型信息
     *
     * @param command 大模型信息
     * @return 结果
     */
    ResponseInfo saveModel(ModelCommand command);

    /**
     * 修改大模型信息
     *
     * @param command 大模型信息
     * @return 结果
     */
    ResponseInfo updateModel(ModelCommand command);

    /**
     * 批量删除大模型信息
     *
     * @param ids 需要删除的大模型信息主键集合
     * @return 结果
     */
    ResponseInfo removeModelByIds(List<Long> ids);

    /**
     * 删除大模型信息信息
     *
     * @param id 大模型信息主键
     * @return 结果
     */
    ResponseInfo removeModelById(Long id);

}
