package com.master.chat.controller.sys;

import com.master.chat.common.util.ExcelUtil;
import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.RoleCommand;
import com.master.chat.sys.pojo.command.RoleResourceAuthCommand;
import com.master.chat.sys.pojo.command.SysUserRoleAuthCommand;
import com.master.chat.sys.service.*;
import com.master.chat.sys.pojo.dto.RoleExcelDTO;
import com.master.chat.sys.pojo.vo.ResourceVO;
import com.master.chat.sys.pojo.vo.RoleVO;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.sys.service.*;
import com.master.common.annotation.Log;
import com.master.common.annotation.RateLimiter;
import com.master.common.annotation.RepeatSubmit;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 角色接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private IRoleResourceService roleResourceService;
    @Autowired
    private IResourceService resourceService;

    /**
     * 获取角色分页列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @RateLimiter(time = 60, count = 10)
    @RepeatSubmit(interval = 1000)
    @PreAuthorize("hasAuthority('sys:role:list')")
    public ResponseInfo<IPageInfo<RoleVO>> pageRole(@RequestParam Map map) {
        return roleService.pageRole(new Query(map, true));
    }

    /**
     * 获取角色列表信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public ResponseInfo<List<RoleVO>> listRole(@RequestParam Map map) {
        return roleService.listRole(new Query(map));
    }

    /**
     * 根据角色id获取角色信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @Version 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public ResponseInfo getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * 添加角色
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:role:save')")
    @Log(type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveRole(@Validated @RequestBody RoleCommand roleCommand) {
        return roleService.saveRole(roleCommand);
    }

    /**
     * 修改角色
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:role:update')")
    @Log(type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateRole(@Validated(UpdateGroup.class) @RequestBody RoleCommand roleCommand) {
        return roleService.updateRole(roleCommand);
    }

    /**
     * 角色启用/禁用
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('sys:role:update')")
    @Log(value = "角色启用/禁用", type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateRoleStatus(@Validated(UpdateGroup.class) @RequestBody RoleCommand roleCommand) {
        return roleService.updateRoleStatus(roleCommand);
    }

    /**
     * 删除角色
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:role:remove')")
    @Log(type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo deleteRole(@PathVariable List<Long> ids) {
        return roleService.removeByRoleIds(ids);
    }


    /**
     * 角色导出
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:role:export')")
    @Log(value = "系统账号导出", type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.EXPORT)
    public void export(HttpServletResponse response, @RequestParam Map map) throws IOException {
        List<RoleVO> roleVOS = roleService.listRole(new Query(map)).getData();
        ExcelUtil.write(response, "角色列表", RoleExcelDTO.class, DozerUtil.convertor(roleVOS, RoleExcelDTO.class));
    }

    /**
     * 根据角色id获取资源信息(包含是否选中状态)
     *
     * @author: Yang
     * @date: 2023/01/31
     * @Version 1.0.0
     */
    @GetMapping("/resource")
    public ResponseInfo<List<ResourceVO>> getResourceByRoleId(@RequestParam("id") Long id) {
        return resourceService.treeResourceByRoleId(id);
    }

    /**
     * 根据角色id获取资源id数组
     *
     * @author: Yang
     * @date: 2023/01/31
     * @Version 1.0.0
     */
    @GetMapping("/resource/ids/{id}")
    public ResponseInfo<List<Long>> getResourceIdsByRoleId(@PathVariable Long id) {
        return roleResourceService.getResourceIdByRoleId(id);
    }

    /**
     * 获取已授权用户列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/user/authorization/page")
    public ResponseInfo<IPageInfo<SysUserVO>> pageAuthorizationUser(@RequestParam Map map) {
        map.put("authorization", StatusEnum.ENABLED.getValue());
        return sysUserService.pageSysUser(new Query(map));
    }

    /**
     * 获取未授权用户列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/user/unauthorization/page")
    public ResponseInfo<IPageInfo<SysUserVO>> pageUnauthorizationUser(@RequestParam Map map) {
        map.put("authorization", StatusEnum.DISABLED.getValue());
        return sysUserService.pageSysUser(new Query(map));
    }

    /**
     * 授权角色资源
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/resource/authorization")
    @Log(value = "授权角色资源", type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.GRANT)
    public ResponseInfo authorizationResource(@RequestBody RoleResourceAuthCommand command) {
        return roleResourceService.authorizationResource(command.getResourceIds(), command.getRoleId(), command.getOperater());
    }

    /**
     * 授权角色用户
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/user/authorization")
    @Log(value = "授权用户角色信息", type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.GRANT)
    public ResponseInfo authorizationUser(@RequestBody SysUserRoleAuthCommand command) {
        return command.isUserIds() ? userRoleService.saveSysUserRole(command.getUserIds(), command.getRoleId(), command.getOperater()) :
                userRoleService.saveSysUserRole(command.getUserId(), command.getRoleIds(), command.getOperater());
    }

    /**
     * 取消授权角色用户
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/user/unauthorization")
    @Log(value = "取消授权用户角色信息", type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.GRANT)
    public ResponseInfo unauthorizationUser(@RequestBody SysUserRoleAuthCommand command) {
        return userRoleService.unauthorizationSysUser(command.getUserIds(), command.getRoleId(), command.getOperater());
    }

}
