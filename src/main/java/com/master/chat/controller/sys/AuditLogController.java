package com.master.chat.controller.sys;

import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.QueryDTO;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.pojo.vo.AuditLogVO;
import com.master.chat.sys.service.IAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审核日志接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/audit-log")
public class AuditLogController extends BaseController {
    @Autowired
    private IAuditLogService auditLogService;

    /**
     * 获取审核日志分页列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    public ResponseInfo<IPageInfo<AuditLogVO>> pageAuditLog(QueryDTO query) {
        return auditLogService.pageAuditLog(query.getId(), query.getPage(), query.getLimit(), query.getType());
    }

    /**
     * 获取审核日志详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping()
    public ResponseInfo<AuditLogVO> getAuditLog(Long id) {
        return auditLogService.getAuditLog(id);
    }

}
