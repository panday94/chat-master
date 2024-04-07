package com.master.chat.controller.sys;

import com.master.chat.framework.base.BaseController;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.ResourceCommand;
import com.master.chat.sys.pojo.dto.SysUserRolesDTO;
import com.master.chat.sys.pojo.vo.ResourceVO;
import com.master.chat.sys.service.IResourceService;
import com.master.chat.sys.service.ISysUserRoleService;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.QueryDTO;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.common.validator.group.AddGroup;
import com.master.chat.common.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 获取所有菜单信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:resource:list')")
    public ResponseInfo<List<ResourceVO>> listResource(QueryDTO query) {
        return resourceService.listResource(query);
    }

    /**
     * 获取所有菜单信息(返回树结构)
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('sys:resource:list')")
    public ResponseInfo<List<ResourceVO>> treeResource() {
        return resourceService.treeResource(false, false, false);
    }

    /**
     * 根据登录账号获取资源信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/owner")
    public ResponseInfo<List<ResourceVO>> listResourceByLoginUser() {
        UserDetail userDetail = getLoginUser();
        SysUserRolesDTO sysUserRoles = sysUserRoleService.getSysUserRoles(userDetail.getId(), userDetail.getUsername()).getData();
        return ResponseInfo.success(resourceService.buildResoureces(resourceService.treeResource(sysUserRoles.getRoleIds(), sysUserRoles.getAdmind()).getData()));
    }

    /**
     * 根据id菜单信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:resource:query')")
    public ResponseInfo<ResourceVO> getResourceById(@PathVariable Long id) {
        return resourceService.getResourceById(id);
    }

    /**
     * 添加菜单资源信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:resource:save')")
    @Log(type = SysLogTypeConstant.RESOURCE, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveResource(@Validated(AddGroup.class) @RequestBody ResourceCommand command) {
        return resourceService.saveResource(command);
    }

    /**
     * 修改菜单资源信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:resource:update')")
    @Log(type = SysLogTypeConstant.RESOURCE, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateResource(@Validated(UpdateGroup.class) @RequestBody ResourceCommand command) {
        return resourceService.updateResource(command);
    }

    /**
     * 启用/禁用菜单资源信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('sys:resource:update')")
    @Log(value = "启用/禁用菜单资源信息", type = SysLogTypeConstant.RESOURCE, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateResourceStatus(@Validated(UpdateGroup.class) @RequestBody ResourceCommand command) {
        return resourceService.updateResourceStatus(command);
    }

    /**
     * 删除菜单资源信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:resource:remove')")
    @Log(type = SysLogTypeConstant.RESOURCE, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo deleteMenuById(@PathVariable Long id) {
        return resourceService.removeResourceById(id);
    }

}
