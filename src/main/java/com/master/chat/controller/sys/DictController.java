package com.master.chat.controller.sys;

import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.DictCommand;
import com.master.chat.sys.pojo.command.DictTypeCommand;
import com.master.chat.sys.pojo.vo.DictTypeVO;
import com.master.chat.sys.pojo.vo.DictVO;
import com.master.chat.sys.service.IDictService;
import com.master.chat.sys.service.IDictTypeService;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 字典接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Autowired
    private IDictTypeService dictTypeService;
    @Autowired
    private IDictService dictService;

    /**
     * 获取字典类型分页列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/type/page")
    @PreAuthorize("hasAuthority('sys:dict:list')")
    public ResponseInfo<IPageInfo<DictTypeVO>> pageDictType(@RequestParam Map map) {
        return dictTypeService.pageDictType(new Query(map, true));
    }

    /**
     * 根据id获取字典类型详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/type/{id}")
    @PreAuthorize("hasAuthority('sys:dict:query')")
    public ResponseInfo<DictTypeVO> getDictType(@PathVariable Long id) {
        return dictTypeService.getDictTypeById(id);
    }

    /**
     * 字典类型保存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/type")
    @PreAuthorize("hasAuthority('sys:dict:save')")
    @Log(value = "字典类型保存", type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveDictType(@RequestBody DictTypeCommand command) {
        return dictTypeService.saveDictType(command);
    }

    /**
     * 字典类型修改
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/type")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    @Log(value = "字典类型修改", type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateDictType(@RequestBody DictTypeCommand command) {
        return dictTypeService.updateDictType(command);
    }

    /**
     * 启用/禁用字典信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/type/status")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    @Log(value = "启用/禁用字典类型信息", type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateDictTypeStatus(@RequestBody DictTypeCommand command) {
        return dictTypeService.updateDictTypeStatus(command);
    }

    /**
     * 字典类型删除
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/type/{ids}")
    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @Log(value = "字典类型删除", type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeDictType(@PathVariable List<Long> ids) {
        return dictTypeService.removeDictTypeByIds(ids);
    }

    /**
     * 刷新字典缓存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/type/cache/refresh")
    @Log(value = "刷新字典缓存", type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.CLEAN)
    public ResponseInfo refreshDictCache() {
        return dictService.refreshDictCache();
    }

    /**
     * 获取字典分页列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:dict:list')")
    public ResponseInfo<IPageInfo<DictVO>> pageDict(@RequestParam Map map) {
        return dictService.pageDict(new Query(map, true));
    }

    /**
     * 获取字典列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:dict:list')")
    public ResponseInfo<List<DictVO>> listByType(String type) {
        return dictService.listDict(type);
    }

    /**
     * 根据id获取字典值详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dict:query')")
    public ResponseInfo<DictVO> getDict(@PathVariable Long id) {
        return dictService.getDictById(id);
    }

    /**
     * 字典保存
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:dict:save')")
    @Log(type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveDict(@RequestBody DictCommand command) {
        return dictService.saveDict(command);
    }

    /**
     * 字典更新
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:dict:update')")
    @Log(type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateDict(@RequestBody DictCommand command) {
        return dictService.updateDict(command);
    }

    /**
     * 启用/禁用字典信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    @Log(value = "启用/禁用字典信息", type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateDictStatus(@RequestBody DictCommand command) {
        return dictService.updateDictStatus(command);
    }

    /**
     * 字典删除
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:dict:remove')")
    @Log(type = SysLogTypeConstant.DICT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeDict(@PathVariable List<Long> ids) {
        return dictService.removeDictByIds(ids);
    }

}
