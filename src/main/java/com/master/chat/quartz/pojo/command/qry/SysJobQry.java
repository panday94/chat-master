package com.master.chat.quartz.pojo.command.qry;

import com.master.chat.common.api.QueryDTO;
import lombok.Data;

/**
 * 任务日志查询参数
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class SysJobQry extends QueryDTO {

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 日志信息
     */
    private String jobMessage;

}
