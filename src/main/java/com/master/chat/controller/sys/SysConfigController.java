package com.master.chat.controller.sys;

import com.master.chat.comm.util.ExcelUtil;
import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.SysConfigCommand;
import com.master.chat.sys.pojo.dto.SysConfigExcelDTO;
import com.master.chat.sys.pojo.vo.SysConfigVO;
import com.master.chat.sys.service.ISysConfigService;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.common.utils.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 参数配置接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/config")
public class SysConfigController extends BaseController {
    @Autowired
    private ISysConfigService configService;

    /**
     * 获取参数配置分页列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:config:list')")
    public ResponseInfo<IPageInfo<SysConfigVO>> pageSysConfig(@RequestParam Map map) {
        return configService.pageConfig(new Query(map, true));
    }

    /**
     * 获取参数配置列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:config:list')")
    public ResponseInfo<List<SysConfigVO>> listSysConfig(@RequestParam Map map) {
        return configService.listConfig(new Query(map));
    }

    /**
     * 根据参数编号获取详细信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sys:config:query')")
    public ResponseInfo<SysConfigVO> getInfo(@PathVariable Long id) {
        return configService.getConfigById(id);
    }


    /**
     * 新增参数配置
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:config:save')")
    @Log(type = SysLogTypeConstant.CONFIG, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo add(@Validated @RequestBody SysConfigCommand command) {
        if (!configService.checkConfigKeyUnique(command)) {
            return ResponseInfo.error("新增参数'" + command.getName() + "'失败，参数键名已存在");
        }
        return configService.saveConfig(command);
    }

    /**
     * 修改参数配置
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:config:update')")
    @Log(type = SysLogTypeConstant.CONFIG, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo edit(@Validated @RequestBody SysConfigCommand command) {
        if (!configService.checkConfigKeyUnique(command)) {
            return ResponseInfo.error("修改参数'" + command.getName() + "'失败，参数键名已存在");
        }
        return configService.updateConfig(command);
    }

    /**
     * 删除参数配置
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:config:remove')")
    @Log(type = SysLogTypeConstant.CONFIG, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo remove(@PathVariable List<Long> ids) {
        return configService.removeConfigByIds(ids);
    }

    /**
     * 刷新配置缓存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/cache/refresh")
    @Log(value = "刷新系统配置缓存", type = SysLogTypeConstant.CONFIG, businessType = BusinessTypeEnum.CLEAN)
    public ResponseInfo refreshCache() {
        return configService.resetConfigCache();
    }

    /**
     * 参数配置列表导出
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:config:export')")
    @Log(value = "系统配置导出", type = SysLogTypeConstant.CONFIG, businessType = BusinessTypeEnum.EXPORT)
    public void export(HttpServletResponse response, @RequestParam Map map) throws IOException {
        List<SysConfigVO> configVOS = configService.listConfig(new Query(map)).getData();
        ExcelUtil.write(response, "系统参数列表", SysConfigExcelDTO.class, DozerUtil.convertor(configVOS, SysConfigExcelDTO.class));
    }

}
