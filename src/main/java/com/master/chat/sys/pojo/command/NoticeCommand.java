package com.master.chat.sys.pojo.command;

import com.master.common.api.CommonCommand;
import com.master.common.validator.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NoticeCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "缺少通知id", groups = UpdateGroup.class)
    @Min(value = 1, message = "通知id必须大于1", groups = UpdateGroup.class)
    private Long id;

    /**
     * 公告类型（1通知 2公告）
     */
    private Integer type;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 状态 0->禁用;1->启用
     */
    private Integer status;

}
