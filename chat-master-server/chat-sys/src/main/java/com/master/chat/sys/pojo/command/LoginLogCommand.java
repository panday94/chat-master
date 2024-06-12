package com.master.chat.sys.pojo.command;

import com.master.chat.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 说明
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class LoginLogCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ids数组
     */
    private List<Long> ids;

}
