package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.OrderCommand;
import com.master.chat.gpt.pojo.vo.OrderVO;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.gpt.service.IOrderService;
import com.master.common.annotation.Log;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  订单接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/order" )
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    /**
     * 查询订单分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:order:list')" )
    public ResponseInfo<IPageInfo<OrderVO>> pageOrder(@RequestParam Map map) {
        return orderService.pageOrder(new Query(map, true));
    }

    /**
     * 查询订单列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:order:list')" )
    public ResponseInfo<List<OrderVO>> listOrder(@RequestParam Map map) {
        return orderService.listOrder(new Query(map));
    }

    /**
     * 获取订单详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:order:query')" )
    public ResponseInfo<OrderVO> getOrderById(@PathVariable("id" ) Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * 新增订单
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:order:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveOrder(@Validated @RequestBody OrderCommand command) {
        return orderService.saveOrder(command);
    }

    /**
     * 修改订单
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:order:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateOrder(@Validated @RequestBody OrderCommand command) {
        return orderService.updateOrder(command);
    }

    /**
     * 批量删除订单
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:order:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeOrderByIds(@PathVariable List<Long> ids) {
        return orderService.removeOrderByIds(ids);
    }

}
