package com.master.chat.gpt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.gpt.mapper.OrderMapper;
import com.master.chat.gpt.pojo.command.OrderCommand;
import com.master.chat.gpt.pojo.entity.Order;
import com.master.chat.gpt.pojo.vo.OrderVO;
import com.master.chat.gpt.service.IOrderService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  订单 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 根据id获取订单信息
     *
     * @param id 订单id
     * @return
     */
    private Order getOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (ValidatorUtil.isNull(order)) {
            throw new ErrorException("订单信息不存在，无法操作");
        }
        return order;
    }

    @Override
    public ResponseInfo<IPageInfo<OrderVO>> pageOrder(Query query) {
        IPage<OrderVO> iPage = orderMapper.pageOrder(new Page<>(query.getCurrent(), query.getSize()), query);
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<OrderVO>> listOrder(Query query) {
        return ResponseInfo.success(orderMapper.listOrder(query));
    }

    @Override
    public ResponseInfo<OrderVO> getOrderById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(getOrder(id), OrderVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveOrder(OrderCommand command) {
        Order order = DozerUtil.convertor(command, Order.class);
        order.setCreateUser(command.getOperater());
        orderMapper.insert(order);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateOrder(OrderCommand command) {
        Order order = getOrder(command.getId());
        DozerUtil.convertor(command, order);
        order.setUpdateUser(command.getOperater());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.updateById(order);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeOrderByIds(List<Long> ids) {
        orderMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeOrderById(Long id) {
        orderMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
