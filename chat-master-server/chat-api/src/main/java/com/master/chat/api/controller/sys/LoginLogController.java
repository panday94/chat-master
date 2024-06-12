package com.master.chat.api.controller.sys;

import com.master.chat.api.base.BaseController;
import com.master.chat.framework.util.ExcelUtil;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.dto.LoginLogExcelDTO;
import com.master.chat.sys.pojo.vo.LoginLogVO;
import com.master.chat.sys.service.ILoginLogService;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.common.utils.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 登录日志接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/login-log")
public class LoginLogController extends BaseController {
    @Autowired
    private ILoginLogService logService;

    /**
     * 获取登录日志列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:loginlog:list')")
    public ResponseInfo<IPageInfo<LoginLogVO>> pageLoginLog(@RequestParam Map map) {
        return logService.pageLoginLog(new Query(map, true));
    }

    /**
     * 删除登录日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:loginlog:remove')")
    @Log(type = SysLogTypeConstant.LOIGIN_LOG, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeLoginLogByIds(@PathVariable List<Long> ids) {
        return logService.removeLoginLogByIds(ids);
    }

    /**
     * 清空登录日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/clean")
    @PreAuthorize("hasAuthority('sys:loginlog:clean')")
    @Log(value = "清空登录日志", type = SysLogTypeConstant.LOIGIN_LOG, businessType = BusinessTypeEnum.CLEAN)
    public ResponseInfo cleanLoginLog() {
        return logService.cleanLoginLog();
    }

    /**
     * 导出登录日志
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:loginlog:export')")
    @Log(value = "登录日志导出", type = SysLogTypeConstant.LOIGIN_LOG, businessType = BusinessTypeEnum.EXPORT)
    public void exportLoginLog(HttpServletResponse response, @RequestParam Map map) throws IOException {
        List<LoginLogVO> loginLogVOS = logService.listLoginLog(new Query(map)).getData();
        ExcelUtil.write(response, "登录日志列表", LoginLogExcelDTO.class, DozerUtil.convertor(loginLogVOS, LoginLogExcelDTO.class));
    }

    /**
     * 获取在线用户列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/online/list")
    @PreAuthorize("hasAuthority('monitor:online:list')")
    public ResponseInfo<IPageInfo<LoginLogVO>> pageOnlineLoginLog(@RequestParam Map map) {
        map.put("online", 1);
        return logService.pageLoginLog(new Query(map, true));
    }

    /**
     * 强退用户
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/force/{ids}")
    @PreAuthorize("hasAuthority('monitor:online:forceLogout')")
    @Log(value = "强退用户", type = SysLogTypeConstant.LOIGIN_LOG, businessType = BusinessTypeEnum.FORCE)
    public ResponseInfo forceLogout(@PathVariable List<Long> ids) {
        return logService.forceLogout(ids);
    }

}
