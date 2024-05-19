package com.master.chat.gpt.pojo.command;

import com.master.chat.common.api.CommonCommand;
import lombok.Data;

import java.io.Serializable;

/**
 *  兑换码对象 Command
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
@Data
public class RedemptionCommand extends CommonCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 兑换码
     */
    private String code;

    /**
     * 可兑次数
     */
    private Long num;

    /**
     * 兑换人
     */
    private Long userId;

    /**
     * 兑换时间
     */
    private Integer recieveTime;

    /**
     * 状态
     */
    private Integer status;

}
