package com.master.chat.api.config;

import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.common.config.dto.BaseInfoDTO;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.IntegerEnum;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.core.constant.BaseConfigConstant;
import com.master.chat.core.mapper.OpenkeyMapper;
import com.master.chat.core.pojo.vo.OpenkeyVO;
import com.master.chat.llm.chatglm.ChatGLMClient;
import com.master.chat.llm.deepseek.DeepSeekClient;
import com.master.chat.llm.doubao.DouBaoClient;
import com.master.chat.llm.internlm.InternlmClient;
import com.master.chat.llm.locallm.LocalLMClient;
import com.master.chat.llm.moonshot.MoonshotClient;
import com.master.chat.llm.openai.OpenAiClient;
import com.master.chat.llm.openai.function.KeyRandomStrategy;
import com.master.chat.llm.spark.SparkClient;
import com.master.chat.llm.tongyi.TongYiClient;
import com.master.chat.llm.wenxin.WenXinClient;
import com.master.chat.sys.service.IBaseConfigService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 初始化大模型bean
 *
 * @author: Yang
 * @date: 2023/9/7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@Configuration
public class InitLLMBean {
    @Resource
    private OpenkeyMapper openkeyMapper;
    @Resource
    private IBaseConfigService baseConfigService;

    @Bean
    public OpenAiClient openAiClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.OPENAI.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到OpenAItoken数据");
            return new OpenAiClient();
        }
        BaseInfoDTO baseInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.BASE_INFO, BaseInfoDTO.class);
        String apiHost = null;
        String[] proxyAddress = null;
        if (baseInfo.getProxyType().equals(IntegerEnum.THREE.getValue()) && ValidatorUtil.isNotNull(baseInfo.getProxyAddress())) {
            if (!baseInfo.getProxyAddress().contains(StringPoolConstant.COLON)) {
                log.error("代理地址错误");
                return new OpenAiClient();
            }
            proxyAddress = baseInfo.getProxyAddress().split(StringPoolConstant.COLON);
        } else if (baseInfo.getProxyType().equals(IntegerEnum.TWO.getValue()) && ValidatorUtil.isNotNull(baseInfo.getProxyServer())) {
            apiHost = baseInfo.getProxyServer();
        }
        return OpenAiClient
                .builder()
                .apiHost(apiHost)
                .apiKey(openkeys.stream().map(v -> v.getAppKey()).collect(Collectors.toList()))
                .proxyAddress(proxyAddress)
                //自定义key使用策略 默认随机策略
                .keyStrategy(new KeyRandomStrategy())
                .build();
    }

    /**
     * DeepSeek
     *
     * @return
     */
    @Bean
    public DeepSeekClient deepSeekClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.DEEPSEEK.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到DeepSeektoken数据");
            return new DeepSeekClient();
        }
        return DeepSeekClient
                .builder()
                .apiKey(openkeys.stream().map(v -> v.getAppKey()).collect(Collectors.toList()))
                //自定义key使用策略 默认随机策略
                .keyStrategy(new KeyRandomStrategy())
                .build();
    }

    /**
     * 文心一言
     *
     * @return
     */
    @Bean
    public WenXinClient wenXinClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.WENXIN.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到文心一言token数据");
            return new WenXinClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey()) || ValidatorUtil.isNull(openkey.getAppSecret())) {
            log.error("未加载到文心一言token数据");
            return new WenXinClient();
        }
        return WenXinClient.builder().logLevel(HttpLoggingInterceptor.Level.BASIC)
                .apiKey(openkey.getAppKey()).secretKey(openkey.getAppSecret()).build();
    }

    /**
     * 通义千问
     *
     * @return
     */
    @Bean
    public TongYiClient tongYiClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.TONGYI.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到通义千问token数据");
            return new TongYiClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey())) {
            log.error("未加载到通义千问token数据");
            return new TongYiClient();
        }
        return TongYiClient.builder().apiKey(openkey.getAppKey()).build();
    }

    /**
     * 讯飞星火
     *
     * @return
     */
    @Bean
    public SparkClient sparkClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.SPARK.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到讯飞星火token数据");
            return new SparkClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey())) {
            log.error("未加载到讯飞星火token数据");
            return new SparkClient();
        }
        return SparkClient.builder().appId(openkey.getAppId()).appKey(openkey.getAppKey()).appSecret(openkey.getAppSecret()).build();
    }

    /**
     * 智谱清言
     *
     * @return
     */
    @Bean
    public ChatGLMClient chatGLMClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.CHATGLM.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到智谱清言token数据");
            return new ChatGLMClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey())) {
            log.error("未加载到智谱清言token数据");
            return new ChatGLMClient();
        }
        return ChatGLMClient.builder().apiKey(openkey.getAppKey()).appSecret(openkey.getAppSecret()).apiSecretKey(openkey.getAppKey()).build();
    }

    /**
     * 月之暗面
     *
     * @return
     */
    @Bean
    public MoonshotClient moonshotClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.MOONSHOT.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到月之暗面token数据");
            return new MoonshotClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey())) {
            log.error("未加载到月之暗面token数据");
            return new MoonshotClient();
        }
        return MoonshotClient.builder().apiKey(openkey.getAppKey()).build();
    }

    /**
     * 豆包
     *
     * @return
     */
    @Bean
    public DouBaoClient douBaoClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.DOUBAO.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到豆包token数据");
            return new DouBaoClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey())) {
            log.error("未加载到豆包token数据");
            return new DouBaoClient();
        }
        return DouBaoClient.builder().apiKey(openkey.getAppKey()).build();
    }

    /**
     * 书生·浦语
     *
     * @return
     */
    @Bean
    public InternlmClient internlmClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.INTERNLM.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            log.error("未加载到书生浦语模型token数据");
            return new InternlmClient();
        }
        OpenkeyVO openkey = openkeys.get(0);
        if (ValidatorUtil.isNull(openkey.getAppKey())) {
            log.error("未加载到书生浦语模型token数据");
            return new InternlmClient();
        }
        return InternlmClient.builder().apiKey(openkey.getAppKey()).build();
    }

    /**
     * LocalLM 本地模型
     * 支持Langchain-Chatchat、Ollama、GiteeAI、扣子、FastGPT、LinkAI、Dify
     *
     * @return
     */
    @Bean
    public LocalLMClient localLMClient() {
        List<OpenkeyVO> openkeys = openkeyMapper.listOpenkeyByModel(ChatModelEnum.LOCALLM.getValue());
        if (ValidatorUtil.isNullIncludeArray(openkeys)) {
            return LocalLMClient.builder().build();
        }
        return LocalLMClient.builder().apiKey(openkeys.get(0).getAppKey()).build();
    }

}
