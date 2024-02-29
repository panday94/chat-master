package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.UserCommand;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IUserService;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  会员用户接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/user" )
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     * 查询会员用户分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:user:list')" )
    public ResponseInfo<IPageInfo<UserVO>> pageUser(@RequestParam Map map) {
        return userService.pageUser(new Query(map, true));
    }

    /**
     * 查询会员用户列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:user:list')" )
    public ResponseInfo<List<UserVO>> listUser(@RequestParam Map map) {
        return userService.listUser(new Query(map));
    }

    /**
     * 获取会员用户详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:user:query')" )
    public ResponseInfo<UserVO> getUserById(@PathVariable("id" ) Long id) {
        return userService.getUserById(id);
    }

    /**
     * 新增会员用户
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:user:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveUser(@Validated @RequestBody UserCommand command) {
        return userService.saveUser(command);
    }

    /**
     * 修改会员用户
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:user:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateUser(@Validated @RequestBody UserCommand command) {
        return userService.updateUser(command);
    }

    /**
     * 批量删除会员用户
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:user:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeUserByIds(@PathVariable List<Long> ids) {
        return userService.removeUserByIds(ids);
    }

}
