package com.master.chat.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.BaseConfigCommand;
import com.master.chat.sys.pojo.vo.BaseConfigVO;
import com.master.chat.sys.service.IBaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 基础配置接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/sys/base-config")
public class BaseConfigController extends BaseController {
    @Autowired
    private IBaseConfigService baseConfigService;

    /**
     * 查询基础配置分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:base:config:list')")
    public ResponseInfo<IPageInfo<BaseConfigVO>> pageBaseConfig(@RequestParam Map map) {
        return baseConfigService.pageBaseConfig(new Query(map, true));
    }

    /**
     * 查询基础配置列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:base:config:list')")
    public ResponseInfo<List<BaseConfigVO>> listBaseConfig(@RequestParam Map map) {
        return baseConfigService.listBaseConfig(new Query(map));
    }

    /**
     * 获取基础配置详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{name}")
    @PreAuthorize("hasAuthority('sys:base:config:query')")
    public ResponseInfo<JSONObject> getBaseConfigByName(@PathVariable("name") String name) {
        return ResponseInfo.success(baseConfigService.getBaseConfigByName(name, JSONObject.class));
    }

    /**
     * 新增基础配置
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:base:config:save')")
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveBaseConfig(@Validated @RequestBody BaseConfigCommand command) {
        return baseConfigService.saveBaseConfig(command);
    }

    /**
     * 修改基础配置
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:base:config:update')")
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateBaseConfig(@Validated @RequestBody BaseConfigCommand command) {
        return baseConfigService.updateBaseConfig(command);
    }

    /**
     * 批量删除基础配置
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:base:config:remove')")
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeBaseConfigByIds(@PathVariable List<Long> ids) {
        return baseConfigService.removeBaseConfigByIds(ids);
    }

}
