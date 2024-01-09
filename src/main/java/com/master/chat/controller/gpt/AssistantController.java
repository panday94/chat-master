package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.AssistantCommand;
import com.master.chat.gpt.pojo.vo.AssistantVO;
import com.master.chat.gpt.service.IAssistantService;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.common.annotation.Log;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  AI助理功能接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/assistant" )
public class AssistantController extends BaseController {
    @Autowired
    private IAssistantService assistantService;

    /**
     * 查询AI助理功能分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:assistant:list')" )
    public ResponseInfo<IPageInfo<AssistantVO>> pageAssistant(@RequestParam Map map) {
        return assistantService.pageAssistant(new Query(map, true));
    }

    /**
     * 查询AI助理功能列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:assistant:list')" )
    public ResponseInfo<List<AssistantVO>> listAssistant(@RequestParam Map map) {
        return assistantService.listAssistant(new Query(map));
    }

    /**
     * 获取AI助理功能详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:assistant:query')" )
    public ResponseInfo<AssistantVO> getAssistantById(@PathVariable("id" ) Long id) {
        return assistantService.getAssistantById(id);
    }

    /**
     * 新增AI助理功能
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:assistant:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveAssistant(@Validated @RequestBody AssistantCommand command) {
        return assistantService.saveAssistant(command);
    }

    /**
     * 修改AI助理功能
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:assistant:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateAssistant(@Validated @RequestBody AssistantCommand command) {
        return assistantService.updateAssistant(command);
    }

    /**
     * 批量删除AI助理功能
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:assistant:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeAssistantByIds(@PathVariable List<Long> ids) {
        return assistantService.removeAssistantByIds(ids);
    }

}
