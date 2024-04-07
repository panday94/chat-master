package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.Redemption;
import com.master.chat.gpt.pojo.vo.RedemptionVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 兑换码 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface RedemptionMapper extends BaseMapper<Redemption> {

    /**
    * 分页查询兑换码列表
    *
    * @param page  分页参数
    * @param query 查询条件
    * @return
    */
    IPage<RedemptionVO> pageRedemption(IPage page, @Param("q") Query query);

    /**
     * 查询兑换码列表
     *
     * @param query 查询条件
     * @return
     */
    List<RedemptionVO> listRedemption(@Param("q") Query query);

    /**
     * 查询兑换码
     *
     * @param query 查询条件
     * @return
     */
    RedemptionVO getRedemption(@Param("q") Query query);

}
