package com.master.chat.controller.app;

import com.master.chat.common.api.ResponseInfo;
import com.master.chat.framework.base.BaseAppController;
import com.master.chat.gpt.pojo.command.OrderCommand;
import com.master.chat.gpt.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单接口
 *
 * @author: Yang
 * @date: 2023/5/4
 * @version: 1.0.0

 */
@RestController(value = "appOrderController")
@RequestMapping("/app/order")
public class OrderController extends BaseAppController {
    @Autowired
    private IOrderService orderService;

    /**
     * 会员购买
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @PostMapping("/buy")
    public ResponseInfo buyMember(@RequestBody OrderCommand command) {
        command.setUserId(getLoginUser().getId());
        return orderService.saveOrder(command);
    }

}
