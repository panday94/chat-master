package com.master.chat.controller.app;

import com.master.chat.comm.constant.OssConstant;
import com.master.chat.comm.util.FileUploadUtils;
import com.master.chat.common.api.FileInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.framework.base.BaseAppController;
import com.master.chat.framework.config.SystemConfig;
import com.master.chat.gpt.pojo.command.SysUserPasswordCommand;
import com.master.chat.gpt.pojo.command.UserCommand;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IModelService;
import com.master.chat.gpt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 会员信息接口
 *
 * @author: Yang
 * @date: 2023/5/4
 * @version: 1.0.0

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
        return userService.getLoginUserById(getLoginUser().getId());
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
        Query query = new Query();
        query.put("status", StatusEnum.ENABLED.getValue());
        return modelService.listModel(query);
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
    @PostMapping("/avatar")
    public ResponseInfo<FileInfo> updateUserAvatar(@RequestParam("file") MultipartFile file) {
        String pathName = "avatar/";
        FileInfo fileInfo = null;
        String filePath = SystemConfig.uploadPath + getPathName(pathName);
        fileInfo = FileUploadUtils.upload(filePath, file);
        fileInfo.setFileUrl(SystemConfig.baseUrl + fileInfo.getFileUrl());
        if (ValidatorUtil.isNull(fileInfo) || ValidatorUtil.isNull(fileInfo.getFileUrl())) {
            return ResponseInfo.validateFail("上传失败");
        }
        userService.updateUserAvatar(getUserId(), fileInfo.getFileUrl());
        return ResponseInfo.success(fileInfo);
    }

    /**
     * 获取文件路径名称
     *
     * @param pathName
     * @return
     */
    private String getPathName(String pathName) {
        if (ValidatorUtil.isNull(pathName)) {
            return OssConstant.DEFAULT_FILE;
        }
        if (pathName.startsWith(StringPoolConstant.SLASH)) {
            pathName = pathName.substring(1, pathName.length());
        }
        if (!pathName.endsWith(StringPoolConstant.SLASH)) {
            pathName = pathName + StringPoolConstant.SLASH;
        }
        return pathName;
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
