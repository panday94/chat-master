package com.master.chat.gpt.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  助手分类对象 VO
 *
 * @author: Yang
 * @date: 2023-11-22
 * @version: 1.0.0

 */
@Data
public class AssistantTypeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 分类名称
     */
    private String name;

    /**
     * icon图标
     */
    private String icon;

    /**
     * 状态 0 禁用 1 启用
     */
    private Integer status;

}
