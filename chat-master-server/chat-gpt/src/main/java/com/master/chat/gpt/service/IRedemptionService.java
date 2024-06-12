package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.gpt.pojo.command.RedemptionCommand;
import com.master.chat.gpt.pojo.entity.Redemption;
import com.master.chat.gpt.pojo.vo.RedemptionVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 兑换码 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface IRedemptionService extends IService<Redemption> {

    /**
     * 查询兑换码分页列表
     *
     * @param query 查询条件
     * @return 兑换码集合
     */
    ResponseInfo<IPageInfo<RedemptionVO>> pageRedemption(Query query);

    /**
     * 查询兑换码列表
     *
     * @param query 查询条件
     * @return 兑换码集合
     */
    ResponseInfo<List<RedemptionVO>> listRedemption(Query query);

    /**
     * 根据主键查询兑换码
     *
     * @param id 兑换码主键
     * @return 兑换码
     */
     ResponseInfo<RedemptionVO> getRedemptionById(Long id);

    /**
     * 新增兑换码
     *
     * @param command 兑换码
     * @return 结果
     */
    ResponseInfo saveRedemption(RedemptionCommand command);

    /**
     * 修改兑换码
     *
     * @param command 兑换码
     * @return 结果
     */
    ResponseInfo updateRedemption(RedemptionCommand command);

    /**
     * 批量删除兑换码
     *
     * @param ids 需要删除的兑换码主键集合
     * @return 结果
     */
    ResponseInfo removeRedemptionByIds(List<Long> ids);

    /**
     * 删除兑换码信息
     *
     * @param id 兑换码主键
     * @return 结果
     */
    ResponseInfo removeRedemptionById(Long id);

}
