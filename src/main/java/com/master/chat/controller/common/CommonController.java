package com.master.chat.controller.common;

import com.master.chat.comm.constant.OssConstant;
import com.master.chat.comm.enums.OssEnum;
import com.master.chat.comm.util.FileUploadUtils;
import com.master.chat.comm.util.RedisUtils;
import com.master.chat.framework.config.SystemConfig;
import com.master.chat.sys.pojo.dto.config.ExtraInfoDTO;
import com.master.chat.sys.pojo.vo.DictVO;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.sys.service.*;
import com.master.chat.common.api.FileInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.api.SelectDTO;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 通用接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/common")
public class CommonController {
    private final IDictTypeService dictTypeService;
    private final IDictService dictService;
    private final IPostService postService;
    private final IRoleService roleService;
    private final IResourceService resourceService;
    private final ISysConfigService configService;
    private final ISysUserService sysUserService;
    private final IBaseConfigService baseConfigService;

    /**
     * 根据系统配置本地/oss上传文件
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @RequestMapping("/file/upload")
    public ResponseInfo<FileInfo> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "pathName", required = false) String pathName) {
        ExtraInfoDTO extraInfo = baseConfigService.getBaseConfigByName("extraInfo", ExtraInfoDTO.class);
        if (ValidatorUtil.isNull(extraInfo) || ValidatorUtil.isNull(extraInfo.getOssType()) || OssEnum.LOCAL.getValue().equals(extraInfo.getOssType())) {
            String filePath = SystemConfig.uploadPath + getPathName(pathName);
            FileInfo fileInfo = FileUploadUtils.upload(filePath, file);
            fileInfo.setFileUrl(SystemConfig.baseUrl + fileInfo.getFileUrl());
            return ResponseInfo.success(fileInfo);
        }
        return ResponseInfo.validateFail("未知的上传文件方式，上传失败");
    }

    /**
     * 上传本地文件
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @RequestMapping("/file/local/upload")
    public ResponseInfo<FileInfo> uploadLocal(@RequestParam("file") MultipartFile file, @RequestParam(value = "pathName", required = false) String pathName) {
        String filePath = SystemConfig.uploadPath + getPathName(pathName);
        FileInfo fileInfo = FileUploadUtils.upload(filePath, file);
        fileInfo.setFileUrl(SystemConfig.baseUrl + fileInfo.getFileUrl());
        return ResponseInfo.success(fileInfo);
    }

    /**
     * 上传文件到oss
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @RequestMapping("/file/oss/upload")
    public ResponseInfo<FileInfo> uploadOss(@RequestParam("file") MultipartFile file, @RequestParam(value = "pathName", required = false) String pathName) {
        ExtraInfoDTO extraInfo = baseConfigService.getBaseConfigByName("extraInfo", ExtraInfoDTO.class);
        if (ValidatorUtil.isNull(extraInfo) || ValidatorUtil.isNull(extraInfo.getOssType()) || OssEnum.LOCAL.getValue().equals(extraInfo.getOssType())) {
            return ResponseInfo.validateFail("未开通oss上传功能");
        }
        return ResponseInfo.validateFail("未知的上传文件方式，上传失败");
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
     * 根据类型获取字典类型列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/dict/type/select")
    public ResponseInfo<List<SelectDTO>> listDictType() {
        return ResponseInfo.success(DozerUtil.convertor(dictTypeService.listDictType(new Query()).getData(), SelectDTO.class));
    }

    /**
     * 根据类型获取字典值列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/dict/select/{type}")
    public ResponseInfo<List<DictVO>> listValueByType(@PathVariable String type) {
        return ResponseInfo.success(dictService.listDict(type).getData());
    }

    /**
     * 获取岗位筛选列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/post/select")
    public ResponseInfo<List<SelectDTO>> listPost() {
        return ResponseInfo.success(DozerUtil.convertor(postService.listPost(new Query()).getData(), SelectDTO.class));
    }

    /**
     * 获取角色筛选列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/role/select")
    public ResponseInfo<List<SelectDTO>> listRole() {
        return ResponseInfo.success(DozerUtil.convertor(roleService.listRole(new Query()).getData(), SelectDTO.class));
    }

    /**
     * 获取菜单筛选列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/resource/tree/select")
    public ResponseInfo<List<SelectDTO>> treeResourceBySelect() {
        return ResponseInfo.success(DozerUtil.convertor(resourceService.treeResource(false, false, true).getData(), SelectDTO.class));
    }

    /**
     * 根据参数键名查询参数值
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping(value = "/config/configKey/{configKey}")
    public ResponseInfo<String> getConfigKey(@PathVariable String configKey) {
        return configService.getConfigByKey(configKey);
    }

    /**
     * 获取人员筛选列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping(value = "/sys-user/select")
    public ResponseInfo<List<SelectDTO>> listSysUser(@RequestParam(required = false) Map map) {
        List<SysUserVO> sysUserVOS = sysUserService.listSysUser(new Query(map)).getData();
        return ResponseInfo.success(DozerUtil.convertor(sysUserVOS, SelectDTO.class));
    }

    public static void main(String[] args) {
        PasswordEncoder _$1 = new StandardPasswordEncoder("JTOPJTOPJTBFCMSTIANDAOCHOUQINGWILLBETHEBESTCMS$");
        System.out.println(_$1.encode("123456"));
    }

}
