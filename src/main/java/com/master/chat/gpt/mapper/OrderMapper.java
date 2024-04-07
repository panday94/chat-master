package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.common.api.Query;
import com.master.chat.gpt.pojo.entity.Order;
import com.master.chat.gpt.pojo.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
    * 分页查询订单列表
    *
    * @param page  分页参数
    * @param query 查询条件
    * @return
    */
    IPage<OrderVO> pageOrder(IPage page, @Param("q") Query query);

    /**
     * 查询订单列表
     *
     * @param query 查询条件
     * @return
     */
    List<OrderVO> listOrder(@Param("q") Query query);

    /**
     * 查询订单
     *
     * @param query 查询条件
     * @return
     */
    OrderVO getOrder(@Param("q") Query query);

}
