package com.master.chat.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.master.chat.common.config.ApplicationConfig;
import com.master.chat.common.config.SystemWarningConfig;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.enums.SpringProfilesEnum;
import com.master.chat.common.exception.SystemException;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 发送企业微信工具类
 *
 * @author: Yang
 * @date: 2020/3/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
public class SystemWarningUtil {

    /**
     * 发送信息
     */
    private static final String SENDMESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    /**
     * 获取token
     */
    private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";


    /**
     * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
     *
     * @param: corpid 应用组织编号
     * @param: corpsecre t应用秘钥
     */
    private static String getToken(String corpid, String corpsecret) {
        String url = String.format(ACCESS_TOKEN_URL, corpid, corpsecret);
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.get(url));
        return jsonObject.getString("access_token");
    }

    /**
     * 发送信息
     *
     * @param toUser  用户
     * @param content 推送内容
     */
    public static void sendWeChat(SystemWarningConfig config, String toUser, String content) {
        String profile = StringPoolConstant.EMPTY;
        if (SpringProfilesEnum.DEV.getValue().equals(ApplicationConfig.active)) {
            profile = "开发环境";
        }
        if (SpringProfilesEnum.TEST.getValue().equals(ApplicationConfig.active)) {
            profile = "测试环境";
        }
        if (SpringProfilesEnum.PROD.getValue().equals(ApplicationConfig.active)) {
            profile = "正式环境";
        }
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        joiner.add("环境：" + profile);
        joiner.add(content);
        String token = getToken(config.getCoprid(), config.getCoprsecret());
        WeChatDataDTO wcd = new WeChatDataDTO();
        wcd.setTouser(toUser);
        wcd.setToparty("@all");
        wcd.setAgentid(config.getAgentid());
        wcd.setMsgtype("text");
        Map<Object, Object> text = new HashMap<>(16);
        text.put("content", joiner.toString());
        wcd.setText(text);
        String result = HttpUtil.post(SENDMESSAGE_URL.concat(token), JSON.toJSONString(wcd));
        log.info("发送微信的响应数据，msg：{}", result);
    }

    /**
     * 发送信息
     *
     * @param toUser  用户
     * @param content 推送内容
     */
    public static void sendWeChat(SystemWarningConfig config, String content) {
        sendWeChat(config, "@all", content);
    }

    /**
     * 系统异常推送
     */
    public static void systemError(SystemWarningConfig config, String url, Exception e) {
        if (!SpringProfilesEnum.PROD.getValue().equals(ApplicationConfig.active)) {
            return;
        }
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        joiner.add("报警时间：" + DateUtil.getCurrentDateTime()).add("接口路径：" + url);
        joiner.add(getExceptionInfo(config, e));
        sendWeChat(config, "@all", joiner.toString());
    }

    /**
     * 系统异常推送
     */
    public static void systemError(SystemWarningConfig config, Exception e) {
        systemError(config, ApplicationContextUtil.getRequest().getRequestURI(), e);
    }

    /**
     * 获取异常信息
     */
    public static String getExceptionInfo(SystemWarningConfig config, Throwable e) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        if (e instanceof SystemException) {
            joiner.add("系统预警：" + (ValidatorUtil.isNull(e.getMessage()) ? ResponseEnum.SYSTEM_WARNING.getMsg() : e.getMessage()));
        } else {
            joiner.add("系统异常：" + e.toString());
        }
        int length = 50;
        for (int i = 0; i < length && i < e.getStackTrace().length; i++) {
            if (e.getStackTrace()[i].toString().indexOf(config.getPackages()) > -1) {
                joiner.add("异常定位：" + e.getStackTrace()[i].toString());
                break;
            }
        }
        return joiner.toString();
    }

    @Data
    private static class WeChatDataDTO {
        /**
         * 成员账号
         */
        private String touser;

        /**
         * 部门账号
         */
        private String toparty;

        /**
         * 消息类型
         */
        private String msgtype;

        /**
         * 企业应用的agentID
         */
        private Integer agentid;

        /**
         * 实际接收Map类型数据
         */
        private Object text;
    }

}
