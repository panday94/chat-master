package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.ModelCommand;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IModelService;
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
 *  大模型信息接口
 *
 * @author: Yang
 * @date: 2023-12-01
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/model" )
public class ModelController extends BaseController {
    @Autowired
    private IModelService modelService;

    /**
     * 查询大模型信息分页列表
     *
     * @author: Yang
     * @date: 2023-12-01
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:model:list')" )
    public ResponseInfo<IPageInfo<ModelVO>> pageModel(@RequestParam Map map) {
        return modelService.pageModel(new Query(map, true));
    }

    /**
     * 查询大模型信息列表
     *
     * @author: Yang
     * @date: 2023-12-01
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:model:list')" )
    public ResponseInfo<List<ModelVO>> listModel(@RequestParam Map map) {
        return modelService.listModel(new Query(map));
    }

    /**
     * 获取大模型信息详细信息
     *
     * @author: Yang
     * @date: 2023-12-01
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:model:query')" )
    public ResponseInfo<ModelVO> getModelById(@PathVariable("id" ) Long id) {
        return modelService.getModelById(id);
    }

    /**
     * 新增大模型信息
     *
     * @author: Yang
     * @date: 2023-12-01
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:model:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveModel(@Validated @RequestBody ModelCommand command) {
        return modelService.saveModel(command);
    }

    /**
     * 修改大模型信息
     *
     * @author: Yang
     * @date: 2023-12-01
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:model:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateModel(@Validated @RequestBody ModelCommand command) {
        return modelService.updateModel(command);
    }

    /**
     * 批量删除大模型信息
     *
     * @author: Yang false
     * @date: 2023-12-01
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:model:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeModelByIds(@PathVariable List<Long> ids) {
        return modelService.removeModelByIds(ids);
    }

}
