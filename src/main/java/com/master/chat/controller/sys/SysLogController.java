package com.master.chat.controller.sys;

import com.master.chat.common.util.ExcelUtil;
import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.dto.SysLogExcelDTO;
import com.master.chat.sys.pojo.vo.SysLogVO;
import com.master.chat.sys.service.ISysLogService;
import com.master.common.annotation.Log;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.BusinessTypeEnum;
import com.master.common.utils.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 系统日志接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/sys-log")
public class SysLogController extends BaseController {
    @Autowired
    private ISysLogService sysLogService;

    /**
     * 获取系统日志分页信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:log:list')")
    public ResponseInfo<IPageInfo<SysLogVO>> pageSysLog(@RequestParam Map map) {
        return sysLogService.pageSysLog(new Query(map, true));
    }

    /**
     * 删除系统日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:log:remove')")
    @Log(type = SysLogTypeConstant.SYS_LOG, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo remove(@PathVariable List<Long> ids) {
        return sysLogService.removeSysLogByIds(ids);
    }

    /**
     * 清空系统日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/clean")
    @PreAuthorize("hasAuthority('sys:log:clean')")
    @Log(type = SysLogTypeConstant.SYS_LOG, businessType = BusinessTypeEnum.CLEAN)
    public ResponseInfo clean() {
        return sysLogService.clearSysLog();
    }

    /**
     * 导出系统日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:log:export')")
    @Log(value = "系统日志导出", type = SysLogTypeConstant.SYS_LOG, businessType = BusinessTypeEnum.EXPORT)
    public void export(HttpServletResponse response, @RequestParam Map map) throws IOException {
        List<SysLogVO> sysLogVOS = sysLogService.listSysLog(new Query(map)).getData();
        ExcelUtil.write(response, "系统日志列表", SysLogExcelDTO.class, DozerUtil.convertor(sysLogVOS, SysLogExcelDTO.class));
    }

}
