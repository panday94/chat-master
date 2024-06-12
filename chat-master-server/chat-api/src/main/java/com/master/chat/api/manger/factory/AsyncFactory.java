package com.master.chat.api.manger.factory;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.master.chat.sys.pojo.entity.SysLog;
import com.master.chat.sys.service.ILoginLogService;
import com.master.chat.sys.service.ISysLogService;
import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.common.enums.LongEnum;
import com.master.chat.framework.util.AddressUtil;
import com.master.chat.framework.util.ApplicationContextUtil;
import com.master.chat.framework.validator.ValidatorUtil;

import java.util.Optional;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
     * 添加系统访问日志
     *
     * @param parameter 请求参数
     * @param result    返回结果
     * @param time      操作时间
     * @param type      操作类型
     * @param operation 操作内容
     * @return
     */
    public static TimerTask addSysLog(SysLog sysLog, String parameter, Object result, long time) {
        return new TimerTask() {
            @Override
            public void run() {
                sysLog.setAddress(AddressUtil.getRealAddressByIP(sysLog.getIp()))
                        .setTime(time)
                        .setParams(parameter)
                        .setResult(getResultStr(result, false));
                ApplicationContextUtil.getBean(ISysLogService.class).saveSysLog(sysLog);
            }
        };
    }

    /**
     * 添加系统操作日志
     *
     * @param parameter    请求参数
     * @param result       返回结果
     * @param time         操作时间
     * @param value        操作内容
     * @param title        操作模块
     * @param businessType 操作类型
     * @param command      命令
     * @return
     */
    public static TimerTask addSysLog(SysLog sysLog, String parameter, Object result, long time, String value, String type,
                                      BusinessTypeEnum businessType, CommonCommand command) {
        return new TimerTask() {
            @Override
            public void run() {
                Long fkId = ValidatorUtil.isNotNull(command) ? command.getId() : LongEnum.ZERO.getValue();
                if (BusinessTypeEnum.INSERT.equals(businessType)) {
                    try {
                        fkId = Optional.ofNullable(getResultStr(result, true)).map(v -> Long.valueOf(v)).orElse(0L);
                    } catch (NumberFormatException e) {
                        fkId = 0L;
                    }
                }
                sysLog.setCreateUser(Optional.ofNullable(command).orElse(new CommonCommand()).getOperater()).setAddress(AddressUtil.getRealAddressByIP(sysLog.getIp()))
                        .setOperation(ValidatorUtil.isNull(value) ? getOperation(type, sysLog.getRequestMethod()) : value)
                        .setFkId(fkId)
                        .setTitle(type)
                        .setBusinessType(businessType.toString())
                        .setTime(time)
                        .setParams(parameter)
                        .setResult(getResultStr(result, false));
                ApplicationContextUtil.getBean(ISysLogService.class).saveSysLog(sysLog);
            }
        };
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

    /**
     * 添加系统审核日志
     *
     * @param parameter 请求参数
     * @param result    返回结果
     * @param time      操作时间
     * @param type      操作类型
     * @param operation 操作内容
     * @return
     */
    public static TimerTask addAuditLog(SysLog sysLog, String parameter, Object result, long time, String type, String operation) {
        return new TimerTask() {
            @Override
            public void run() {
            }
        };
    }

    /**
     * 记录登录信息
     *
     * @param sysUserId     用户id
     * @param sessionId     会话标识
     * @param username      用户名
     * @param status        状态
     * @param authorization 身份标识
     * @param msg           登录信息
     * @return 任务task
     */
    public static TimerTask addLoginLog(final Long sysUserId, final String username, final Integer status, final String authorization, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                // 封装对象
                ApplicationContextUtil.getBean(ILoginLogService.class).saveLoginLog(sysUserId, UUID.randomUUID().toString(),
                        username, status, authorization, msg);

            }
        };
    }

}
