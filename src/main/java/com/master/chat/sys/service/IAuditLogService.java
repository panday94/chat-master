package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.enums.AuditEnum;
import com.master.chat.sys.pojo.command.AuditCommand;
import com.master.chat.sys.pojo.entity.AuditLog;
import com.master.chat.sys.pojo.vo.AuditLogVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.ResponseInfo;

/**
 * 审核日志 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public interface IAuditLogService extends IService<AuditLog> {

    /**
     * 获取审核日志内容
     *
     * @param id      审核记录id
     * @param current 当前页
     * @param size    数量
     * @param type    审核内容类型 {@link AuditEnum}
     * @return
     */
    ResponseInfo<IPageInfo<AuditLogVO>> pageAuditLog(Long id, Long current, Long size, Integer type);

    /**
     * 获取审核日志内容
     *
     * @param id 审核记录id
     * @return
     */
    ResponseInfo<AuditLogVO> getAuditLog(Long id);

    /**
     * 添加审核日志
     *
     * @param command 审核信息
     * @return
     */
    ResponseInfo saveAuditLog(AuditCommand command);

}
