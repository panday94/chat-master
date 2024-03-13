package com.master.chat.sys.service.impl;

import com.master.chat.sys.mapper.StatisticsMapper;
import com.master.chat.sys.service.IStatisticsService;
import com.master.chat.common.api.Query;
import com.master.chat.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 统计接口实现类
 *
 * @author: yang
 * @date: 2023/1/13
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Object> getTotalData(Query query) {
        return statisticsMapper.getTotalData(query);
    }

    @Override
    public List<Map<String, Object>> getLineData(Query query) {
        return statisticsMapper.getLineData(query);
    }

    @Override
    public List<List<Object>> getRaddarData(Query query) {
        List<Map<String, Object>> lists = statisticsMapper.getRaddarData(query);
        List<List<Object>> result = new ArrayList<>();
        lists.stream().forEach(v -> {
            List<Object> object = new ArrayList<>();
            String date = String.valueOf(v.get("date"));
            object.add(DateUtil.getCurrentWeek(DateUtil.parseLocalDate(date).getDayOfWeek().getValue()));
            object.add(v.get("insertCount"));
            object.add(v.get("updateCount"));
            object.add(v.get("deleteCount"));
            result.add(object);
        });
        return result;
    }

    @Override
    public Map<String, Object> getPieData(Query query) {
        return statisticsMapper.getPieData(query);
    }

    @Override
    public List<Map<String, Object>> getBarData(Query query) {
        return statisticsMapper.getBarData(query);
    }

}
