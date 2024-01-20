package com.master.chat.controller.app;

import com.master.chat.common.util.AliyunOSSUtil;
import com.master.chat.framework.base.BaseAppController;
import com.master.chat.gpt.pojo.command.UserCommand;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IModelService;
import com.master.chat.gpt.service.IUserService;
import com.master.chat.sys.pojo.command.SysUserPasswordCommand;
import com.master.common.api.FileInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 修改个人信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    public ResponseInfo updateUser(@RequestBody UserCommand command) {
        command.setId(getUserId());
        return userService.updateUser(command);
    }

    /**
     * 修改账号头像
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/avatar")
    public ResponseInfo<FileInfo> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) {
        FileInfo fileInfo = AliyunOSSUtil.uploadFile(file, "demo/" + "avatar/");
        userService.updateUserAvatar(getUserId(), fileInfo.getFilePath());
        return ResponseInfo.success(fileInfo);
    }

    /**
     * 修改密码
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/password/update")
    public ResponseInfo updatePassword(@RequestBody SysUserPasswordCommand command) {
        return userService.updatePassword(command);
    }

    /**
     * 开启/关闭上下文
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @PutMapping("/context")
    public ResponseInfo openContext(@RequestBody UserCommand command) {
        return userService.updateUserContext(command);
    }

}
