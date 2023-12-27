package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.vo.RedemptionVO;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.gpt.pojo.command.RedemptionCommand;
import com.master.chat.gpt.service.IRedemptionService;
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
 *  兑换码接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/redemption" )
public class RedemptionController extends BaseController {
    @Autowired
    private IRedemptionService redemptionService;

    /**
     * 查询兑换码分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:redemption:list')" )
    public ResponseInfo<IPageInfo<RedemptionVO>> pageRedemption(@RequestParam Map map) {
        return redemptionService.pageRedemption(new Query(map, true));
    }

    /**
     * 查询兑换码列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:redemption:list')" )
    public ResponseInfo<List<RedemptionVO>> listRedemption(@RequestParam Map map) {
        return redemptionService.listRedemption(new Query(map));
    }

    /**
     * 获取兑换码详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:redemption:query')" )
    public ResponseInfo<RedemptionVO> getRedemptionById(@PathVariable("id" ) Long id) {
        return redemptionService.getRedemptionById(id);
    }

    /**
     * 新增兑换码
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:redemption:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveRedemption(@Validated @RequestBody RedemptionCommand command) {
        return redemptionService.saveRedemption(command);
    }

    /**
     * 修改兑换码
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:redemption:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateRedemption(@Validated @RequestBody RedemptionCommand command) {
        return redemptionService.updateRedemption(command);
    }

    /**
     * 批量删除兑换码
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:redemption:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeRedemptionByIds(@PathVariable List<Long> ids) {
        return redemptionService.removeRedemptionByIds(ids);
    }

}
