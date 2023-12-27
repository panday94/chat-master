package com.master.chat.controller.app;

import com.master.chat.framework.base.BaseAppController;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IModelService;
import com.master.chat.gpt.service.IUserService;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会员信息接口
 *
 * @author: yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController(value = "appUserController")
@RequestMapping("/app/user")
public class UserController extends BaseAppController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IModelService modelService;

    /**
     * 获取用户信息接口
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping()
    public ResponseInfo<UserVO> getUserInfo() {
        return userService.getUserById(getLoginUser().getId());
    }

    /**
     * 获取用户模型接口
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/model")
    public ResponseInfo<List<ModelVO>> getUserModel() {
        return modelService.listModel(new Query());
    }

}
