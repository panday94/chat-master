package com.master.chat.client.model.dto;

import com.master.chat.common.exception.ValidateException;
import com.master.chat.framework.validator.ValidatorUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 公共查询DTO
 *
 * @author: Yang
 * @date: 2023/6/28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDTO implements Serializable {

    private static final long serialVersionUID = -7087813491172330937L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 当前页号
     */
    private Long page;

    /**
     * 页数
     */
    private Long limit;

    /**
     * 当前页号 v1.2
     */
    private Long current;

    /**
     * 页数 v1.2
     */
    private Long size;

    /**
     * 开始时间  yyyy-MM-dd HH:mm:ss
     */
    private String startTime;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;

    /**
     * 开始日期 yyyy-MM-dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy-MM-dd
     */
    private String endDate;

    /**
     * 名称
     */
    private String name;

    /**
     * 操作人姓名
     */
    private String operater;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 查询参数
     */
    private Map<String, Object> params;

    /**
     * 判断查询是否有分页必须参数
     */
    public void assertPage() {
        boolean flag1 = ValidatorUtil.isNullOrZero(this.page) || ValidatorUtil.isNullOrZero(this.limit);
        boolean flag2 = ValidatorUtil.isNullOrZero(this.current) || ValidatorUtil.isNullOrZero(this.size);
        if (flag1 && flag2) {
            throw new ValidateException("缺少分页必要参数");
        }
    }

    public QueryDTO(Long id) {
        this.id = id;
    }

}
