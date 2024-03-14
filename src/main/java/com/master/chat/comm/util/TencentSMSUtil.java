package com.master.chat.comm.util;

import com.master.chat.sys.pojo.dto.config.ExtraInfoDTO;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云短信工具类
 *
 * @author: yang
 * @date: 2023/2/23
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
public class TencentSMSUtil {

    /**
     * 地域节点
     */
    private static String REGION;

    /**
     * 应用id
     */
    private static String APPID;

    /**
     * secretId
     */
    private static String SECREID;

    /**
     * secretKey
     */
    private static String SECREKEY;

    /**
     * 短信签名
     */
    private static String SMS_SIGN;

    /**
     * 初始化配置信息
     */
    public static void initSmsInfo(ExtraInfoDTO smsInfo) {
        REGION = smsInfo.getSmsRegionId();
        APPID = smsInfo.getSmsAppId();
        SECREID = smsInfo.getSmsKeyId();
        SECREKEY = smsInfo.getSmsKeySecret();
        SMS_SIGN = smsInfo.getSmsSign();
    }

    /**
     * 发送短信
     *
     * @param tel        手机号
     * @param templateId 短信模板
     * @param map        短信参数
     */
    public static void sendSms(ExtraInfoDTO smsInfo, String tel, String templateId, String[] params) {
        initSmsInfo(smsInfo);
        Credential cred = new Credential(SECREID, SECREKEY);
        // 实例化一个http选项，可选，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        // 设置代理（无需要直接忽略）
        // httpProfile.setProxyHost("真实代理ip");
        // httpProfile.setProxyPort(真实代理端口);
        httpProfile.setReqMethod("POST");
        /* SDK有默认的超时时间，非必要请不要进行调整
         * 如有需要请在代码中查阅以获取最新的默认值 */
        httpProfile.setConnTimeout(60);
        /* 指定接入地域域名，默认就近地域接入域名为 sms.tencentcloudapi.com ，也支持指定地域域名访问，例如广州地域的域名为 sms.ap-guangzhou.tencentcloudapi.com */
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        /* 非必要步骤:
         * 实例化一个客户端配置对象，可以指定超时时间等配置 */
        ClientProfile clientProfile = new ClientProfile();
        /* SDK默认用TC3-HMAC-SHA256进行签名
         * 非必要请不要修改这个字段 */
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        /* 实例化要请求产品(以sms为例)的client对象
         * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
        SmsClient client = new SmsClient(cred, REGION, clientProfile);
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(APPID);
        req.setSignName(SMS_SIGN);
        req.setTemplateId(templateId);
        req.setTemplateParamSet(params);
        /* 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
         * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号 */
        String[] phoneNumberSet = {tel};
        req.setPhoneNumberSet(phoneNumberSet);

        /* 用户的 session 内容（无需要可忽略）: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
        String sessionContext = "";
        req.setSessionContext(sessionContext);

        /* 短信码号扩展号（无需要可忽略）: 默认未开通，如需开通请联系 [腾讯云短信小助手] */
        String extendCode = "";
        req.setExtendCode(extendCode);

        /* 国际/港澳台短信 SenderId（无需要可忽略）: 国内短信填空，默认未开通，如需开通请联系 [腾讯云短信小助手] */
        String senderid = "";
        req.setSenderId(senderid);

        /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
         * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
        SendSmsResponse res;
        try {
            res = client.SendSms(req);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
        // 输出json格式的字符串回包
        log.info(SendSmsResponse.toJsonString(res));
    }

}
