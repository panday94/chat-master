package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.gpt.pojo.command.CombCommand;
import com.master.chat.gpt.pojo.entity.Comb;
import com.master.chat.gpt.pojo.vo.CombVO;

import java.util.List;

/**
 * 会员套餐 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface ICombService extends IService<Comb> {

    /**
     * 查询会员套餐分页列表
     *
     * @param query 查询条件
     * @return 会员套餐集合
     */
    ResponseInfo<IPageInfo<CombVO>> pageComb(Query query);

    /**
     * 查询会员套餐列表
     *
     * @param query 查询条件
     * @return 会员套餐集合
     */
    ResponseInfo<List<CombVO>> listComb(Query query);

    /**
     * 根据主键查询会员套餐
     *
     * @param id 会员套餐主键
     * @return 会员套餐
     */
     ResponseInfo<CombVO> getCombById(Long id);

    /**
     * 新增会员套餐
     *
     * @param command 会员套餐
     * @return 结果
     */
    ResponseInfo saveComb(CombCommand command);

    /**
     * 修改会员套餐
     *
     * @param command 会员套餐
     * @return 结果
     */
    ResponseInfo updateComb(CombCommand command);

    /**
     * 批量删除会员套餐
     *
     * @param ids 需要删除的会员套餐主键集合
     * @return 结果
     */
    ResponseInfo removeCombByIds(List<Long> ids);

    /**
     * 删除会员套餐信息
     *
     * @param id 会员套餐主键
     * @return 结果
     */
    ResponseInfo removeCombById(Long id);

}
