package com.master.chat.api.controller.app;

import com.master.chat.api.base.BaseAppController;
import com.master.chat.common.config.dto.ExtraInfoDTO;
import com.master.chat.common.constant.OssConstant;
import com.master.chat.common.enums.OssEnum;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.framework.config.SystemConfig;
import com.master.chat.framework.util.AliyunOSSUtil;
import com.master.chat.framework.util.file.FileUploadUtils;
import com.master.chat.framework.util.TencentCOSUtil;
import com.master.chat.gpt.pojo.command.UserCommand;
import com.master.chat.gpt.pojo.command.UserPasswordCommand;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IModelService;
import com.master.chat.gpt.service.IUserService;
import com.master.chat.sys.service.IBaseConfigService;
import com.master.chat.framework.util.file.FileInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.framework.validator.ValidatorUtil;
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
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController(value = "appUserController")
@RequestMapping("/app/user")
public class UserController extends BaseAppController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IModelService modelService;
    @Autowired
    private IBaseConfigService baseConfigService;

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
        ExtraInfoDTO extraInfo = baseConfigService.getBaseConfigByName("extraInfo", ExtraInfoDTO.class);
        FileInfo fileInfo = null;
        if (ValidatorUtil.isNull(extraInfo) || ValidatorUtil.isNull(extraInfo.getOssType()) || OssEnum.LOCAL.getValue().equals(extraInfo.getOssType())) {
            String filePath = SystemConfig.uploadPath + getPathName(pathName);
            fileInfo = FileUploadUtils.upload(filePath, file);
            fileInfo.setFileUrl(SystemConfig.baseUrl + fileInfo.getFileUrl());
        } else if (OssEnum.ALI.getValue().equals(extraInfo.getOssType())) {
            fileInfo = AliyunOSSUtil.uploadFile(extraInfo, file, getPathName(pathName));
        } else if (OssEnum.TECENT.getValue().equals(extraInfo.getOssType())) {
            fileInfo = TencentCOSUtil.upload(extraInfo, file, FileUploadUtils.getPathName(pathName));
        } else {
            return ResponseInfo.validateFail("未知的上传文件方式，上传失败");
        }
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
    public ResponseInfo updatePassword(@RequestBody UserPasswordCommand command) {
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
