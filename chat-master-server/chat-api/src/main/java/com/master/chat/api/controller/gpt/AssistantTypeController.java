package com.master.chat.api.controller.gpt;

import com.master.chat.api.base.BaseController;
import com.master.chat.gpt.pojo.command.AssistantTypeCommand;
import com.master.chat.gpt.pojo.vo.AssistantTypeVO;
import com.master.chat.gpt.service.IAssistantTypeService;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  助手分类接口
 *
 * @author: Yang
 * @date: 2023-11-22
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/assistant-type" )
public class AssistantTypeController extends BaseController {
    @Autowired
    private IAssistantTypeService assistantTypeService;

    /**
     * 查询助手分类分页列表
     *
     * @author: Yang
     * @date: 2023-11-22
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:assistant:type:list')" )
    public ResponseInfo<IPageInfo<AssistantTypeVO>> pageAssistantType(@RequestParam Map map) {
        return assistantTypeService.pageAssistantType(new Query(map, true));
    }

    /**
     * 查询助手分类列表
     *
     * @author: Yang
     * @date: 2023-11-22
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:assistant:type:list')" )
    public ResponseInfo<List<AssistantTypeVO>> listAssistantType(@RequestParam Map map) {
        return assistantTypeService.listAssistantType(new Query(map));
    }

    /**
     * 获取助手分类详细信息
     *
     * @author: Yang
     * @date: 2023-11-22
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:assistant:type:query')" )
    public ResponseInfo<AssistantTypeVO> getAssistantTypeById(@PathVariable("id" ) Long id) {
        return assistantTypeService.getAssistantTypeById(id);
    }

    /**
     * 新增助手分类
     *
     * @author: Yang
     * @date: 2023-11-22
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:assistant:type:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveAssistantType(@Validated @RequestBody AssistantTypeCommand command) {
        return assistantTypeService.saveAssistantType(command);
    }

    /**
     * 修改助手分类
     *
     * @author: Yang
     * @date: 2023-11-22
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:assistant:type:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateAssistantType(@Validated @RequestBody AssistantTypeCommand command) {
        return assistantTypeService.updateAssistantType(command);
    }

    /**
     * 批量删除助手分类
     *
     * @author: Yang false
     * @date: 2023-11-22
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:assistant:type:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeAssistantTypeByIds(@PathVariable List<Long> ids) {
        return assistantTypeService.removeAssistantTypeByIds(ids);
    }

}
