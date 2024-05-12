package com.master.chat.api.base.config;

import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.xfyun.SparkClient;
import com.master.chat.gpt.mapper.OpenkeyMapper;
import com.master.chat.gpt.pojo.vo.OpenkeyVO;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 初始化大模型bean
 *
 * @author: Yang
 * @date: 2023/9/7
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@Configuration
public class InitBean {
    @Autowired
    private OpenkeyMapper openkeyMapper;

    /**
     * 讯飞星火
     *
     * @return
     */
    @Bean
    public SparkClient sparkClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.SPARK.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到智谱清言模型token数据，请添加后需要重启系统");
            return new SparkClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        return new SparkClient(openkey.getAppId(), openkey.getAppKey(), openkey.getAppSecret());
    }


}
