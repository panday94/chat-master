package com.master.chat.framework.manger.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.validator.ValidatorUtil;


/**
 * 异步工厂（产生任务用）
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0

 */
public class AsyncFactory {
    /**
     * 字符串data
     */
    private static final String DATA = "data";

    /**
     * 处理系统返回日志
     *
     * @param result
     * @return
     */
    private static String getResultStr(Object result, boolean data) {
        String resultStr = StringPoolConstant.EMPTY;
        if (ValidatorUtil.isNull(result)) {
            return resultStr;
        }
        if (result instanceof ResponseInfo && data) {
            ResponseInfo response = JSON.parseObject(JSON.toJSONString(result), ResponseInfo.class);
            return String.valueOf(response.getData());
        }
        JSONObject resultJson = JSON.parseObject(JSON.toJSONString(result));
        if (ValidatorUtil.isNotNull(resultJson.get(DATA))) {
            resultJson.remove(DATA);
        }
        resultStr = resultJson.toJSONString();
        return resultStr;
    }


    /**
     * 获取操作详情
     *
     * @param title         操作标题
     * @param requestMethod 请求方式
     * @return
     */
    private static String getOperation(String title, String requestMethod) {
        switch (requestMethod) {
            case "GET":
                return title + "信息获取";
            case "POST":
                return title + "信息添加/修改";
            case "PUT":
                return title + "信息修改";
            case "DELETE":
                return title + "信息删除";
            default:
                return title;
        }
    }

}
