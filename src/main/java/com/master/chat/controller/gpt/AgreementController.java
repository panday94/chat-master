package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.AgreementCommand;
import com.master.chat.gpt.pojo.vo.AgreementVO;
import com.master.chat.gpt.service.IAgreementService;
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
 *  内容管理接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/content" )
public class AgreementController extends BaseController {
    @Autowired
    private IAgreementService contentService;

    /**
     * 查询内容管理分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:content:list')" )
    public ResponseInfo<IPageInfo<AgreementVO>> pageContent(@RequestParam Map map) {
        return contentService.pageContent(new Query(map, true));
    }

    /**
     * 查询内容管理列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:content:list')" )
    public ResponseInfo<List<AgreementVO>> listContent(@RequestParam Map map) {
        return contentService.listContent(new Query(map));
    }

    /**
     * 获取内容管理详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:content:query')" )
    public ResponseInfo<AgreementVO> getContentById(@PathVariable("id" ) Long id) {
        return contentService.getContentById(id);
    }

    /**
     * 新增内容管理
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:content:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveContent(@Validated @RequestBody AgreementCommand command) {
        return contentService.saveContent(command);
    }

    /**
     * 修改内容管理
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:content:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateContent(@Validated @RequestBody AgreementCommand command) {
        return contentService.updateContent(command);
    }

    /**
     * 批量删除内容管理
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:content:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeContentByIds(@PathVariable List<Long> ids) {
        return contentService.removeContentByIds(ids);
    }

}
