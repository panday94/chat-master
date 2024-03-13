package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.UploadConfigCommand;
import com.master.chat.gpt.pojo.vo.UploadConfigVO;
import com.master.chat.gpt.service.IUploadConfigService;
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
 *  缩略图配置接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/upload-config" )
public class UploadConfigController extends BaseController {
    @Autowired
    private IUploadConfigService uploadConfigService;

    /**
     * 查询缩略图配置分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:upload:config:list')" )
    public ResponseInfo<IPageInfo<UploadConfigVO>> pageUploadConfig(@RequestParam Map map) {
        return uploadConfigService.pageUploadConfig(new Query(map, true));
    }

    /**
     * 查询缩略图配置列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:upload:config:list')" )
    public ResponseInfo<List<UploadConfigVO>> listUploadConfig(@RequestParam Map map) {
        return uploadConfigService.listUploadConfig(new Query(map));
    }

    /**
     * 获取缩略图配置详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:upload:config:query')" )
    public ResponseInfo<UploadConfigVO> getUploadConfigById(@PathVariable("id" ) Long id) {
        return uploadConfigService.getUploadConfigById(id);
    }

    /**
     * 新增缩略图配置
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:upload:config:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveUploadConfig(@Validated @RequestBody UploadConfigCommand command) {
        return uploadConfigService.saveUploadConfig(command);
    }

    /**
     * 修改缩略图配置
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:upload:config:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateUploadConfig(@Validated @RequestBody UploadConfigCommand command) {
        return uploadConfigService.updateUploadConfig(command);
    }

    /**
     * 批量删除缩略图配置
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:upload:config:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeUploadConfigByIds(@PathVariable List<Long> ids) {
        return uploadConfigService.removeUploadConfigByIds(ids);
    }

}
