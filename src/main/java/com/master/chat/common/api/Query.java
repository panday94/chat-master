package com.master.chat.common.api;

import com.master.chat.common.enums.IntEnum;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.common.validator.base.BaseAssert;
import com.master.chat.common.xss.SQLFilter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 公共查询参数
 *
 * @author: Yang
 * @date: 2023/8/17
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class Query extends LinkedHashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 当前页码
     */
    private Long page;
    /**
     * 每页条数
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
     * 环比开始日期 yyyy-MM-dd
     */
    private String preStartDate;

    /**
     * 环比结束日期 yyyy-MM-dd
     */
    private String preEndDate;

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

    public Query() {
    }

    public Query(Long id) {
        this.id = id;
        this.put("id", id);
    }

    public Query(Map<String, Object> params) {
        putAllPar(params);
    }

    public Query(Map<String, Object> params, Boolean isPage) {
        putAllPar(params);
        if (!isPage) {
            return;
        }
        boolean flag1 = ValidatorUtil.isNullOrZero(this.page) || ValidatorUtil.isNullOrZero(this.limit);
        boolean flag2 = ValidatorUtil.isNullOrZero(this.current) || ValidatorUtil.isNullOrZero(this.size);
        if (flag1 && flag2) {
            throw new ValidateException("缺少分页必要参数");
        }
    }

    /**
     * 重新处理query参数
     *
     * @param params 查询参数
     */
    public void putAllPar(Map<String, Object> params) {
        this.putAll(params);
        this.id = ValidatorUtil.isNotNull(params.get("id")) ? Long.valueOf(params.get("id").toString()) : null;
        this.page = ValidatorUtil.isNotNull(params.get("page")) ? Long.valueOf(params.get("page").toString()) : null;
        this.limit = ValidatorUtil.isNotNull(params.get("limit")) ? Long.valueOf(params.get("limit").toString()) : null;
        this.current = ValidatorUtil.isNotNull(params.get("current")) ? Long.valueOf(params.get("current").toString()) : null;
        this.size = ValidatorUtil.isNotNull(params.get("size")) ? Long.valueOf(params.get("size").toString()) : null;
        this.startTime = BaseAssert.getParamOrElse(params, "startTime");
        this.endTime = BaseAssert.getParamOrElse(params, "endTime");
        this.startDate = BaseAssert.getParamOrElse(params, "startDate");
        this.endDate = BaseAssert.getParamOrElse(params, "endDate");
        this.preStartDate = BaseAssert.getParamOrElse(params, "preStartDate");
        this.preEndDate = BaseAssert.getParamOrElse(params, "preEndDate");
        this.name = BaseAssert.getParamOrElse(params, "name");
        this.operater = BaseAssert.getParamOrElse(params, "operater");
        this.tel = BaseAssert.getParamOrElse(params, "tel");
        this.status = ValidatorUtil.isNotNull(params.get("status")) ? Integer.valueOf(params.get("status").toString()) : null;
        this.type = ValidatorUtil.isNotNull(params.get("type")) ? Integer.valueOf(params.get("type").toString()) : null;
        if (ValidatorUtil.isNotNull(this.startDate) && this.startDate.length() == IntEnum.TEN.getValue()) {
            this.startDate = this.startDate + " 00:00:00";
            this.put("startDate", this.startDate);
        }
        if (ValidatorUtil.isNotNull(this.endDate) && this.endDate.length() == IntEnum.TEN.getValue()) {
            this.endDate = this.endDate + " 23:59:59";
            this.put("endDate", this.endDate);
        }
        if (ValidatorUtil.isNotNull(this.preStartDate) && this.preStartDate.length() == IntEnum.TEN.getValue()) {
            this.preStartDate = this.preStartDate + " 00:00:00";
            this.put("preStartDate", this.preStartDate);
        }
        if (ValidatorUtil.isNotNull(this.preEndDate) && this.preEndDate.length() == IntEnum.TEN.getValue()) {
            this.preEndDate = this.preEndDate + " 23:59:59";
            this.put("preEndDate", this.preEndDate);
        }
        if (ValidatorUtil.isNotNull(this.page) && ValidatorUtil.isNotNull(this.limit)) {
            this.put("offset", (page - 1) * limit);
        }
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");
        if (StringUtils.isNotBlank(sidx)) {
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if (StringUtils.isNotBlank(order)) {
            this.put("order", SQLFilter.sqlInject(order));
        }
    }

    /**
     * 获取String值
     *
     * @param param
     * @return
     */
    public String getString(String param) {
        return Optional.ofNullable(this.get(param)).map(v -> v.toString()).orElse(null);
    }

    /**
     * 获取Long值
     *
     * @param param
     * @return
     */
    public Long getLong(String param) {
        if (ValidatorUtil.isNull(getString(param))) {
            return null;
        }
        return Long.valueOf(getString(param));
    }

    /**
     * 获取Integer值
     *
     * @param param
     * @return
     */
    public Integer getIntger(String param) {
        if (ValidatorUtil.isNull(getString(param))) {
            return null;
        }
        return Integer.valueOf(getString(param));
    }

    /**
     * 获取BigDecimal值
     *
     * @param param
     * @return
     */
    public BigDecimal getBigDecimal(String param) {
        if (ValidatorUtil.isNull(getString(param))) {
            return null;
        }
        return new BigDecimal(getString(param));
    }

}
