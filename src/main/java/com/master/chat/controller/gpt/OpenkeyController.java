package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.OpenkeyCommand;
import com.master.chat.gpt.pojo.vo.OpenkeyVO;
import com.master.chat.gpt.service.IOpenkeyService;
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
 *  openai token接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/openkey" )
public class OpenkeyController extends BaseController {
    @Autowired
    private IOpenkeyService openkeyService;

    /**
     * 查询openai token分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:openkey:list')" )
    public ResponseInfo<IPageInfo<OpenkeyVO>> pageOpenkey(@RequestParam Map map) {
        return openkeyService.pageOpenkey(new Query(map, true));
    }

    /**
     * 查询openai token列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:openkey:list')" )
    public ResponseInfo<List<OpenkeyVO>> listOpenkey(@RequestParam Map map) {
        return openkeyService.listOpenkey(new Query(map));
    }

    /**
     * 获取openai token详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:openkey:query')" )
    public ResponseInfo<OpenkeyVO> getOpenkeyById(@PathVariable("id" ) Long id) {
        return openkeyService.getOpenkeyById(id);
    }

    /**
     * 新增openai token
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:openkey:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveOpenkey(@Validated @RequestBody OpenkeyCommand command) {
        return openkeyService.saveOpenkey(command);
    }

    /**
     * 修改openai token
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:openkey:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateOpenkey(@Validated @RequestBody OpenkeyCommand command) {
        return openkeyService.updateOpenkey(command);
    }

    /**
     * 批量删除openai token
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:openkey:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeOpenkeyByIds(@PathVariable List<Long> ids) {
        return openkeyService.removeOpenkeyByIds(ids);
    }

}
