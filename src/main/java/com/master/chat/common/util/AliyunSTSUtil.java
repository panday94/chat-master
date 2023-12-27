package com.master.chat.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.master.chat.framework.properties.AliProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 阿里云获取授权工具类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Component
@Slf4j
public class AliyunSTSUtil {
    @Autowired
    private AliProperties aliProperties;

    /**
     * 阿里云账号id
     */
    private static final String ACCOUNT_ID;

    /**
     * 阿里云API的外网域名
     */
    private static final String REGION_ID;

    /**
     * 阿里云API的密钥Access Key ID
     */
    private static String ACCESS_KEY_ID;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    private static String ACCESS_KEY_SECRET;

    /**
     * 静态块
     */
    static {
        //初始化ACCOUNT_ID
        ACCOUNT_ID = "";
        //初始化REGION_ID
        REGION_ID = "";
    }

    @PostConstruct
    public void init() {
        ACCESS_KEY_ID = aliProperties.getOss().getKey();
        ACCESS_KEY_SECRET = aliProperties.getOss().getSecret();
    }

    /**
     * 获取阿里云OSS客户端对象
     */
    private static IAcsClient getAcsClient() {
        //构建一个阿里云客户端，用于发起请求。
        //构建阿里云客户端时需要设置AccessKey ID和AccessKey Secret。
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }

    /**
     * 获取临时身份
     *
     * @return
     */
    public static AssumeRoleResponse.Credentials getAssumeRole() {
        //构造请求，设置参数。关于参数含义和设置方法，请参见API参考。
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRegionId(REGION_ID);
        request.setRoleArn(String.format("acs:ram::%s:role/AliyunOSSTokenGeneratorRole", ACCOUNT_ID));
        request.setRoleSessionName("master");
        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = getAcsClient().getAcsResponse(request);
            return response.getCredentials();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            log.info("ErrCode:{},ErrMsg:{},RequestId{}", e.getErrCode(), e.getErrMsg(), e.getRequestId());
        }
        return null;
    }

}
