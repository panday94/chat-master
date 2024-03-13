package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.CombCommand;
import com.master.chat.gpt.pojo.vo.CombVO;
import com.master.chat.gpt.service.ICombService;
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
 *  会员套餐接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/comb" )
public class CombController extends BaseController {
    @Autowired
    private ICombService combService;

    /**
     * 查询会员套餐分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:comb:list')" )
    public ResponseInfo<IPageInfo<CombVO>> pageComb(@RequestParam Map map) {
        return combService.pageComb(new Query(map, true));
    }

    /**
     * 查询会员套餐列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:comb:list')" )
    public ResponseInfo<List<CombVO>> listComb(@RequestParam Map map) {
        return combService.listComb(new Query(map));
    }

    /**
     * 获取会员套餐详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:comb:query')" )
    public ResponseInfo<CombVO> getCombById(@PathVariable("id" ) Long id) {
        return combService.getCombById(id);
    }

    /**
     * 新增会员套餐
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:comb:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveComb(@Validated @RequestBody CombCommand command) {
        return combService.saveComb(command);
    }

    /**
     * 修改会员套餐
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:comb:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateComb(@Validated @RequestBody CombCommand command) {
        return combService.updateComb(command);
    }

    /**
     * 批量删除会员套餐
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:comb:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeCombByIds(@PathVariable List<Long> ids) {
        return combService.removeCombByIds(ids);
    }

}
