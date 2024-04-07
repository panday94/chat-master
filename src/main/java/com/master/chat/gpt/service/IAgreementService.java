package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.gpt.pojo.command.AgreementCommand;
import com.master.chat.gpt.pojo.entity.Agreement;
import com.master.chat.gpt.pojo.vo.AgreementVO;

import java.util.List;

/**
 * 内容管理 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface IAgreementService extends IService<Agreement> {

    /**
     * 查询内容管理分页列表
     *
     * @param query 查询条件
     * @return 内容管理集合
     */
    ResponseInfo<IPageInfo<AgreementVO>> pageContent(Query query);

    /**
     * 查询内容管理列表
     *
     * @param query 查询条件
     * @return 内容管理集合
     */
    ResponseInfo<List<AgreementVO>> listContent(Query query);

    /**
     * 根据主键查询内容管理
     *
     * @param id 内容管理主键
     * @return 内容管理
     */
     ResponseInfo<AgreementVO> getContentById(Long id);

    /**
     * 新增内容管理
     *
     * @param command 内容管理
     * @return 结果
     */
    ResponseInfo saveContent(AgreementCommand command);

    /**
     * 修改内容管理
     *
     * @param command 内容管理
     * @return 结果
     */
    ResponseInfo updateContent(AgreementCommand command);

    /**
     * 批量删除内容管理
     *
     * @param ids 需要删除的内容管理主键集合
     * @return 结果
     */
    ResponseInfo removeContentByIds(List<Long> ids);

    /**
     * 删除内容管理信息
     *
     * @param id 内容管理主键
     * @return 结果
     */
    ResponseInfo removeContentById(Long id);

}
