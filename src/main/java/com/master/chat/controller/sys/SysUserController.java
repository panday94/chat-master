package com.master.chat.controller.sys;

import com.alibaba.excel.EasyExcel;
import com.master.chat.common.constant.SysConfigConstants;
import com.master.chat.common.util.AliyunOSSUtil;
import com.master.chat.common.util.ExcelUtil;
import com.master.chat.framework.base.BaseController;
import com.master.chat.framework.listener.ExcelListener;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.SysUserCommand;
import com.master.chat.sys.pojo.command.SysUserPasswordCommand;
import com.master.chat.sys.pojo.command.SysUserRegistCommand;
import com.master.chat.sys.pojo.dto.SysUserExcelDTO;
import com.master.chat.sys.pojo.vo.ContactUserVO;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.sys.service.ISysConfigService;
import com.master.chat.sys.service.ISysUserService;
import com.master.common.annotation.Log;
import com.master.common.api.FileInfo;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.BusinessTypeEnum;
import com.master.common.enums.StatusEnum;
import com.master.common.utils.DozerUtil;
import com.master.common.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 员工账号接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysConfigService configService;

    /**
     * 获取账号分页信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResponseInfo<IPageInfo<SysUserVO>> pageSysUser(@RequestParam(required = false) Map map) {
        return sysUserService.pageSysUser(new Query(map, true));
    }

    /**
     * 获取账号列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResponseInfo<List<SysUserVO>> listSysUser(@RequestParam(required = false) Map map) {
        return sysUserService.listSysUser(new Query(map));
    }

    /**
     * 获取好友列表
     *
     * @author: wangfu
     * @date: @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/contact")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResponseInfo<List<ContactUserVO>> listContactSysUser(@RequestParam(required = false) Map map) {
        return sysUserService.listContactSysUser(new Query(map));
    }

    /**
     * 根据id获取账号详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public ResponseInfo<SysUserVO> getSysUserById(@PathVariable Long id) {
        return sysUserService.getSysUserById(id);
    }

    /**
     * 根据用户名或者手机号获取账号详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/name")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public ResponseInfo<SysUserVO> getSysUserByUsername(String name) {
        return sysUserService.getSysUserByUsername(name);
    }

    /**
     * 根据token获取账号详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping()
    public ResponseInfo<SysUserVO> getLoginSysUser() {
        return sysUserService.getSysUserById(getLoginUser().getId());
    }

    /**
     * 添加账号信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:user:save')")
    @Log(type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveSysUser(@Validated @RequestBody SysUserCommand command) {
        return sysUserService.saveSysUser(command);
    }

    /**
     * 账号注册
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/register")
    public ResponseInfo register(@Validated @RequestBody SysUserRegistCommand registCommand) {
        Boolean flag = configService.getKeyValue(SysConfigConstants.REGISTER_ON_OFF);
        if (!flag) {
            return ResponseInfo.error("注册功能已关闭，无法注册！");
        }
        configService.validateCaptcha(registCommand.getCode(), registCommand.getUuid());
        SysUserCommand command = DozerUtil.convertor(registCommand, SysUserCommand.class);
        command.setStatus(StatusEnum.DISABLED.getValue());
        return sysUserService.saveSysUser(command);
    }

    /**
     * 修改账号信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Log(type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateSysUser(@Validated(UpdateGroup.class) @RequestBody SysUserCommand command) {
        return sysUserService.updateSysUser(command);
    }

    /**
     * 修改账号头像
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/avatar")
    @Log(value = "修改账号头像", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo<FileInfo> updateSysUserAvatar(@RequestParam("avatarFile") MultipartFile file, @RequestParam("pathName") String pathName) {
        FileInfo fileInfo = AliyunOSSUtil.uploadFile(file, "demo/" + pathName);
        sysUserService.updateSysUserAvatar(getSysUserId(), fileInfo.getFilePath());
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
    @Log(value = "修改密码", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updatePassword(@RequestBody SysUserPasswordCommand command) {
        return sysUserService.updatePassword(command);
    }

    /**
     * 设置账号为超级管理员
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/admind")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Log(value = "设置账号为超级管理员", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateSysUserAdmind(@RequestBody SysUserCommand command) {
        return sysUserService.sysUserAdmind(command.getId());
    }

    /**
     * 启用/禁用账号信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Log(value = "启用/禁用账号信息", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateSysUserStatus(@RequestBody SysUserCommand command) {
        return sysUserService.updateSysUserStatus(command);
    }

    /**
     * 重置用户密码
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/password/reset")
    @PreAuthorize("hasAuthority('sys:user:resetPwd')")
    @Log(value = "重置用户密码", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo resetPassword(@RequestBody SysUserPasswordCommand command) {
        return sysUserService.resetPassword(command);
    }

    /**
     * 删除账号信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:user:remove')")
    @Log(type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeSysUser(@PathVariable List<Long> ids) {
        return sysUserService.removeBySysUserIds(ids);
    }

    /**
     * 系统账号模版下载
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/template/download")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil.write(response, "用户模版", SysUserExcelDTO.class, new ArrayList<SysUserExcelDTO>());
    }

    /**
     * 系统账号导入
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/import")
    @PreAuthorize("hasAuthority('sys:user:import')")
    @Log(value = "系统账号导入", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.IMPORT)
    public ResponseInfo importSysUser(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), SysUserExcelDTO.class, new ExcelListener(sysUserService)).sheet().doRead();
        return ResponseInfo.success();
    }

    /**
     * 系统账号导出
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:user:export')")
    @Log(value = "系统账号导出", type = SysLogTypeConstant.SYS_USER, businessType = BusinessTypeEnum.EXPORT)
    public void export(HttpServletResponse response, @RequestParam Map map) throws IOException {
        List<SysUserVO> sysUserVOS = sysUserService.listSysUser(new Query(map)).getData();
        ExcelUtil.write(response, "用户列表", SysUserExcelDTO.class, DozerUtil.convertor(sysUserVOS, SysUserExcelDTO.class));
    }

}
