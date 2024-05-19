package com.master.chat.sys.pojo.command;

import com.master.chat.sys.enums.AuditEnum;
import com.master.chat.sys.pojo.entity.AuditLog;
import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.utils.DozerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 审核信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "审核记录id不能为null")
    private Long auditId;

    /**
     * 审核状态 0->审核拒绝;1->审核通过
     */
    @NotNull(message = "审核状态不合法")
    @Range(min = 0, max = 1, message = "审核状态不合法")
    private Integer status;

    /**
     * 审核通过/拒绝原因
     */
    private String reason;

    /**
     * 审核类型
     */
    private AuditEnum auditEnum;

    /**
     * 审核内容
     */
    private String content;

    /**
     * 图片
     */
    private String pic;

    /**
     * 备注
     */
    private String remark;

    public static AuditLog transferToEntity(AuditCommand command) {
        AuditLog log = DozerUtil.convertor(command, AuditLog.class);
        log.setFkId(command.getAuditId());
        log.setType(command.getAuditEnum().getType());
        log.setSysUserId(command.getOperaterId());
        log.setCreateUser(command.getOperater());
        return log;
    }

}
