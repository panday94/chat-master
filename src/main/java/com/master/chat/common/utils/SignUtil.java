package com.master.chat.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.master.chat.common.validator.ValidatorUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * 签名工具
 *
 * @author: Yang
 * @date: 2021/8/12 17:18
 * @version: 1.0.0
 * Copyright Ⓒ 2021 Master Computer Corporation Limited All rights reserved.
 */
public class SignUtil {

    public static String jsonObject(Object obj) {
        Map<String, Object> mid = sortMap(BeanUtil.beanToMap(obj));
        return JSONObject.toJSONString(mid);
    }

    public static String andString(Object obj) {
        Map<String, Object> mid = sortMap(BeanUtil.beanToMap(obj));
        return JSONObject.toJSONString(mid);
    }

    /**
     * NotEmpty true 空值不参加排
     */
    public static String andString(Map<String, Object> obj, Boolean NotEmpty) {
        Map<String, Object> mid = sortMap(obj);
        StringJoiner sj = new StringJoiner("&", "", "");
        for (Map.Entry<String, Object> entry : mid.entrySet()) {
            if (NotEmpty && ValidatorUtil.isNull(entry.getValue())) {
                continue;
            }
            sj.add(entry.getKey() + "=" + entry.getValue());
        }
        return sj.toString();
    }

    /**
     * upOrlower 1 大写 2 小写
     */
    public static String getSing(Object obj, Integer upOrlower) throws UnsupportedEncodingException {
        String pram = jsonObject(obj);
        if (Integer.valueOf(1).equals(upOrlower)) {
            return DigestUtils.md5Hex(pram.getBytes("utf-8")).toUpperCase();
        } else {
            return DigestUtils.md5Hex(pram.getBytes("utf-8")).toLowerCase();
        }
    }

    public static String getSingByString(String obj, Integer upOrlower) {
        try {
            if (Integer.valueOf(1).equals(upOrlower)) {
                return DigestUtils.md5Hex(obj.getBytes("utf-8")).toUpperCase();
            } else {
                return DigestUtils.md5Hex(obj.getBytes("utf-8")).toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取毫秒值
     */
    public static Long getEpochMilli() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    protected static String base64Encode(byte[] md5ResultBytes) {
        Base64.Encoder be = Base64.getEncoder();
        String b64Result = be.encodeToString(md5ResultBytes);
        return b64Result;
    }

    private static Map<String, Object> sortMap(Map<String, Object> info) {
        Map<String, Object> res = new LinkedHashMap<>();
        Set<String> keys = info.keySet();
        Object[] arr = keys.toArray();
        Arrays.sort(arr);
        for (Object key : arr) {
            if (info.get(key) instanceof Map) {
                res.put(key + "", sortMap((Map) info.get(key)));
            } else {
                res.put(key + "", info.get(key));
            }
        }
        return res;
    }
}
