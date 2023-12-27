package com.master.chat.controller.sys;

import com.master.chat.sys.service.IStatisticsService;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.utils.DateUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

/**
 * 统计接口
 *
 * @author: yang
 * @date: 2023/1/13
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private IStatisticsService statisticsService;

    /**
     * 获取总数据
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/index/total")
    public ResponseInfo getTotalData(@RequestParam Map map) {
        return ResponseInfo.success(statisticsService.getTotalData(new Query(map)));
    }

    /**
     * 获取折线图数据
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/index/line")
    public ResponseInfo getLineData(@RequestParam Map map) {
        Query query = new Query(map);
        if (ValidatorUtil.isNull(query.getStartDate()) && ValidatorUtil.isNull(query.getEndDate())) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = LocalDate.now().plusWeeks(-1);
            query.put("startDate", DateUtil.formatLocalDate(startDate));
            query.put("endDate", DateUtil.formatLocalDate(endDate));
        }
        return ResponseInfo.success(statisticsService.getLineData(new Query(query)));
    }

    /**
     * 获取雷达图数据
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/index/raddar")
    public ResponseInfo getRaddarData(@RequestParam Map map) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().plusWeeks(-1);
        map.put("startDate", DateUtil.formatLocalDate(startDate));
        map.put("endDate", DateUtil.formatLocalDate(endDate));
        return ResponseInfo.success(statisticsService.getRaddarData(new Query(map)));
    }

    /**
     * 获取饼图数据
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/index/pie")
    public ResponseInfo getPieData(@RequestParam Map map) {
        return ResponseInfo.success(statisticsService.getPieData(new Query(map)));
    }

    /**
     * 获取柱状图图数据
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/index/bar")
    public ResponseInfo getBarData(@RequestParam Map map) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().plusWeeks(-1);
        map.put("startDate", DateUtil.formatLocalDate(startDate));
        map.put("endDate", DateUtil.formatLocalDate(endDate));
        return ResponseInfo.success(statisticsService.getBarData(new Query(map)));
    }

}
