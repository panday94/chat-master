package com.master.chat.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.master.chat.common.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 获取地址工具类
 *
 * @author: Yang
 * @date: 2021/10/20
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
public class AddressUtil {

    /**
     * IP地址查询
     */
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    /**
     * 未知地址
     */
    public static final String UNKNOWN = "XX XX";

    /**
     * 根据ip获取城市地址
     *
     * @param ip
     * @return
     */
    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IPUtil.internalIp(ip)) {
            return "内网IP";
        }
        try {
            String rspStr = HttpUtil.get(String.format(IP_URL, ip));
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }

}
