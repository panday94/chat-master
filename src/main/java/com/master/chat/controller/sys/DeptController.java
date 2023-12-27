package com.master.chat.controller.sys;

import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.DeptCommand;
import com.master.chat.sys.service.IDeptService;
import com.master.chat.sys.pojo.vo.DeptVO;
import com.master.common.annotation.Log;
import com.master.common.api.QueryDTO;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.StringPoolConstant;
import com.master.common.enums.BusinessTypeEnum;
import com.master.common.validator.group.UpdateGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * 部门接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
    @Autowired
    private IDeptService deptService;

    /**
     * 获取部门树列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/tree")
    public ResponseInfo<List<DeptVO>> treeDept(QueryDTO query) {
        return deptService.treeDept(query);
    }

    /**
     * 获取部门列表（排除节点）
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list/exclude/{id}")
    public ResponseInfo<List<DeptVO>> listDeptExcludeId(@PathVariable Long id) {
        List<DeptVO> deptVOS = deptService.listDept(new QueryDTO()).getData();
        Iterator<DeptVO> it = deptVOS.iterator();
        while (it.hasNext()) {
            DeptVO d = (DeptVO) it.next();
            if (d.getId().intValue() == id || ArrayUtils.contains(StringUtils.split(d.getTreePath(), StringPoolConstant.DASH), id + StringPoolConstant.EMPTY)) {
                it.remove();
            }
        }
        return ResponseInfo.success(deptVOS);
    }

    /**
     * 获取部门列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public ResponseInfo<List<DeptVO>> listDept(QueryDTO query) {
        return deptService.listDept(query);
    }

    /**
     * 根据id获取部门详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:query')")
    public ResponseInfo<DeptVO> getDept(@PathVariable Long id) {
        return deptService.getDeptById(id);
    }

    /**
     * 添加部门信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:dept:save')")
    @Log(type = SysLogTypeConstant.DEPT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveDept(@Validated @RequestBody DeptCommand command) {
        return deptService.saveDept(command);
    }

    /**
     * 修改部门信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:dept:update')")
    @Log(type = SysLogTypeConstant.DEPT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateDept(@Validated(UpdateGroup.class) @RequestBody DeptCommand command) {
        return deptService.updateDept(command);
    }

    /**
     * 启用/禁用部门信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('sys:dept:update')")
    @Log(value = "启用/禁用部门信息", type = SysLogTypeConstant.DEPT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateDeptStatus(@RequestBody DeptCommand command) {
        return deptService.updateDeptStatus(command);
    }

    /**
     * 根据id删除部门信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:remove')")
    @Log(type = SysLogTypeConstant.DEPT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeById(@PathVariable Long id) {
        return deptService.removeById(id);
    }

}
