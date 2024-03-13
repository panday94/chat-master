package com.master.chat.gpt.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.base.exception.ChatMasterException;
import com.master.chat.api.openai.OpenAiClient;
import com.master.chat.api.openai.entity.chat.ChatChoice;
import com.master.chat.api.openai.entity.chat.ChatCompletion;
import com.master.chat.api.openai.entity.chat.ChatCompletionResponse;
import com.master.chat.api.openai.entity.chat.OpenAiMessage;
import com.master.chat.api.qianwen.QianWenClient;
import com.master.chat.api.wenxin.WenXinClient;
import com.master.chat.api.wenxin.constant.ModelE;
import com.master.chat.api.wenxin.entity.ChatResponse;
import com.master.chat.api.wenxin.entity.MessageItem;
import com.master.chat.api.xfyun.SparkClient;
import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.SparkSyncChatResponse;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.entity.response.SparkTextUsage;
import com.master.chat.api.zhipu.ZhiPuClient;
import com.master.chat.comm.constant.SysConfigConstants;
import com.master.chat.framework.base.BaseEntity;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.mapper.ModelMapper;
import com.master.chat.gpt.mapper.OpenkeyMapper;
import com.master.chat.gpt.mapper.UserMapper;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.entity.User;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.gpt.service.IChatService;
import com.master.chat.gpt.service.IGptService;
import com.master.chat.sys.service.ISysConfigService;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.IntegerEnum;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ProhibitVisitException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.common.validator.base.BaseAssert;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v3.ModelApiRequest;
import com.zhipu.oapi.service.v3.ModelApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * chatgpt接口实现类
 *
 * @author: yang
 * @date: 2023/5/5
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@Service
public class GptServiceImpl implements IGptService {
    @Autowired
    private IChatService chatService;
    @Autowired
    private IChatMessageService chatMessageService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OpenkeyMapper openkeyMapper;
    @Autowired
    private ISysConfigService configService;
    private static OpenAiClient openAiClient;
    private static WenXinClient wenXinClient;
    private static ZhiPuClient zhiPuClient;

    private static QianWenClient qianWenClient;
    private static SparkClient sparkClient;


    @Autowired
    public void setGptClient(OpenAiClient openAiClient, WenXinClient wenXinClient, ZhiPuClient zhiPuClient, QianWenClient qianWenClient, SparkClient sparkClient) {
        GptServiceImpl.openAiClient = openAiClient;
        GptServiceImpl.wenXinClient = wenXinClient;
        GptServiceImpl.zhiPuClient = zhiPuClient;
        GptServiceImpl.qianWenClient = qianWenClient;
        GptServiceImpl.sparkClient = sparkClient;
    }

    @Override
    public ResponseInfo<ModelVO> getModel(String model) {
        Query query = new Query();
        query.put("model", model);
        return ResponseInfo.success(modelMapper.getModel(query));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo<ChatMessageVO> chat(GptCommand command) {
        BaseAssert.isBlankOrNull(command.getModel(), "缺少模型信息");
        BaseAssert.isBlankOrNull(command.getPrompt(), "缺少prompt");
        ChatModelEnum modelEnum = ChatModelEnum.getEnum(command.getModel());
        if (ValidatorUtil.isNull(modelEnum)) {
            throw new BusinessException("未知的模型类型，功能未接入");
        }
        //校验用户
        if (!command.getApi()) {
            validateUser(command.getUserId());
        }
        Long chatId = chatService.saveChat(command).getData();
        List<ChatMessageDTO> messages = chatMessageService.saveChatMessage(command, chatId).getData();
        if (ValidatorUtil.isNullIncludeArray(messages)) {
            throw new BusinessException("消息发送失败");
        }
        ChatMessageDTO userMessage = messages.get(messages.size() - 1);
        ChatMessageCommand chatMessage;
        try {
            switch (modelEnum) {
                case CHAT_GPT:
                    chatMessage = chatByOpenAi(chatId, command.getModelVersion(), messages);
                    break;
                case WENXIN:
                    chatMessage = chatByWenXin(chatId, command.getModelVersion(), messages);
                    break;
                case QIANWEN:
                    chatMessage = chatByQianWen(chatId, command.getModelVersion(), messages);
                    break;
                case SPARK:
                    chatMessage = chatBySpark(chatId, command.getModelVersion(), messages);
                    break;
                case ZHIPU:
                    chatMessage = chatByZhiPu(chatId, command.getModelVersion(), messages, command.getQuery());
                    break;
                default:
                    throw new BusinessException("未知的模型类型，功能未接入");
            }
        } catch (ChatMasterException e) {
            chatMessageService.updateMessageStatus(userMessage.getMessageId(), IntegerEnum.THREE.getValue());
            return ResponseInfo.success();
        }
        chatMessage.setParentMessageId(userMessage.getMessageId());
        chatMessageService.saveChatMessage(chatMessage);
        chatMessageService.updateMessageStatus(userMessage.getMessageId(), IntegerEnum.TWO.getValue());
        updateToken(chatMessage.getAppKey(), chatMessage.getUsedTokens(), chatMessage.getMessageId());
        return ResponseInfo.success(DozerUtil.convertor(chatMessage, ChatMessageVO.class));
    }

    /**
     * openai接口聊天
     *
     * @param chatMessages
     * @return
     */
    private ChatMessageCommand chatByOpenAi(Long chatId, String version, List<ChatMessageDTO> chatMessages) {
        if (ValidatorUtil.isNull(openAiClient)) {
            throw new BusinessException("ChatGpt无有效token，请切换其他模型进行聊天");
        }
        List<OpenAiMessage> openAiMessages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            OpenAiMessage currentMessage = OpenAiMessage.builder().content(v.getContent()).role(v.getRole()).build();
            openAiMessages.add(currentMessage);
        });
        ChatCompletion chatCompletion = ChatCompletion.builder()
                .messages(openAiMessages)
                .model(ValidatorUtil.isNotNull(version) ? version : ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .build();
        ChatCompletionResponse response;
        try {
            response = openAiClient.chatCompletion(chatCompletion);
        } catch (Exception e) {
            throw new ChatMasterException("OpenAi接口请求异常，请稍后再试");
        }
        ChatChoice choice = response.getChoices().get(0);
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getId())
                .model(ChatModelEnum.CHAT_GPT.getValue()).modelVersion(response.getModel())
                .content(choice.getMessage().getContent()).role(choice.getMessage().getRole()).finishReason(choice.getFinishReason())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(openAiClient.getApiKey().get(0)).usedTokens(response.getUsage().getTotalTokens())
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    /**
     * 文心一言模型聊天
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    private ChatMessageCommand chatByWenXin(Long chatId, String version, List<ChatMessageDTO> chatMessages) {
        if (ValidatorUtil.isNull(wenXinClient)) {
            throw new BusinessException("文心一言无有效token，请切换其他模型进行聊天");
        }
        List<MessageItem> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            messages.add(MessageItem.builder().role(v.getRole()).content(v.getContent()).build());
        });
        ModelE modelE = ModelE.ERNIE_Bot_turbo;
        if (ValidatorUtil.isNotNull(version)) {
            modelE = ModelE.getEnum(version);
        }
        if (ValidatorUtil.isNull(modelE)) {
            throw new BusinessException("未知的模型");
        }
        ResponseInfo<ChatResponse> wenxinResponse = wenXinClient.chat(messages, modelE);
        if (!wenxinResponse.isSuccess()) {
            throw new ChatMasterException(wenxinResponse.getMsg());
        }
        ChatResponse response = wenxinResponse.getData();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getId())
                .model(ChatModelEnum.WENXIN.getValue()).modelVersion(modelE.getLabel())
                .content(response.getResult()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(wenXinClient.getApiKey()).usedTokens(response.getUsage().getTotalTokens())
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    /**
     * 智谱清言模型聊天
     * 支持超拟人大模型，将modelId替换为characterglm
     * query中增加参数{meta：{"user_info": "我是陆星辰","bot_info:"苏梦远，本名苏远心，是一位当红的国内女歌手及演员。", "user_name": "陆星辰", "bot_name": "苏梦远"}}
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    private ChatMessageCommand chatByZhiPu(Long chatId, String version, List<ChatMessageDTO> chatMessages, Query query) {
        if (ValidatorUtil.isNull(zhiPuClient)) {
            throw new BusinessException("智谱清言无有效token，请切换其他模型进行聊天");
        }
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(v.getRole(), v.getContent());
            prompts.add(prompt);
        });
        ModelApiRequest request = new ModelApiRequest();
        request.setModelId(ValidatorUtil.isNotNull(version) ? version : Constants.ModelChatGLM6BAsync);
        request.setPrompt(prompts);
        // 关闭搜索示例
        //  modelApiRequest.setRef(new HashMap<String, Object>(){{
        //    put("enable",false);
        // }});
        // 开启搜索示例
        // modelApiRequest.setRef(new HashMap<String, Object>(){{
        //    put("enable",true);
        //    put("search_query","历史");
        //  }});
        ModelApiResponse response = zhiPuClient.chat(request, query);
        if (!response.isSuccess()) {
            throw new ChatMasterException(response.getMsg());
        }
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getData().getTaskId())
                .model(ChatModelEnum.ZHIPU.getValue()).modelVersion(request.getModelId())
                .content(response.getData().getChoices().get(0).getContent()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(zhiPuClient.getAppKey()).usedTokens(Long.valueOf(response.getData().getUsage().getTotalTokens()))
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    /**
     * 通义千问模型聊天
     * 文档地址 https://help.aliyun.com/zh/dashscope/developer-reference/api-details?spm=a2c4g.11186623.0.0.234374fakjtImN
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    private ChatMessageCommand chatByQianWen(Long chatId, String version, List<ChatMessageDTO> chatMessages) {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(20);
        chatMessages.stream().forEach(v -> {
            msgManager.add(Message.builder().role(v.getRole()).content(v.getContent()).build());
        });
        QwenParam param = QwenParam.builder().apiKey(qianWenClient.getAppKey())
                .model(ValidatorUtil.isNotNull(version) ? version : Generation.Models.QWEN_TURBO)
                .messages(msgManager.get())
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .topP(0.3)
                .enableSearch(true)
                .build();
        try {
            GenerationResult response = gen.call(param);
            List<GenerationOutput.Choice> choices = response.getOutput().getChoices();
            ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(response.getRequestId())
                    .model(ChatModelEnum.QIANWEN.getValue()).modelVersion(param.getModel())
                    .content(choices.get(0).getMessage().getContent()).finishReason(choices.get(0).getFinishReason())
                    .role(ChatRoleEnum.ASSISTANT.getValue()).status(ChatStatusEnum.SUCCESS.getValue())
                    .appKey(param.getApiKey()).usedTokens(Long.valueOf(response.getUsage().getInputTokens() + response.getUsage().getOutputTokens()))
                    .response(JSON.toJSONString(response))
                    .build();
            return chatMessage;
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            e.printStackTrace();
            throw new ChatMasterException(e.getMessage());
        }
    }

    /**
     * 讯飞星火模型聊天
     *
     * @param chatId
     * @param chatMessages
     * @return
     */
    private ChatMessageCommand chatBySpark(Long chatId, String version, List<ChatMessageDTO> chatMessages) {
        if (ValidatorUtil.isNull(sparkClient)) {
            throw new BusinessException("讯飞星火无有效token，请切换其他模型进行聊天");
        }
        List<SparkMessage> messages = new ArrayList<>();
        chatMessages.stream().filter(d -> !d.getRole().equals(ChatRoleEnum.SYSTEM.getValue())).forEach(v -> {
            SparkMessage currentMessage = new SparkMessage(v.getRole(), v.getContent());
            messages.add(currentMessage);
        });
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传,取值为[1,4096],默认为2048
                .maxTokens(2048)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.5)
                // 指定请求版本，默认使用最新2.0版本
                .apiVersion(ValidatorUtil.isNotNull(version) ? SparkApiVersion.getEnum(version) : SparkApiVersion.V2_0)
                .chatId(chatId.toString())
                .build();
        SparkSyncChatResponse response = sparkClient.chat(sparkRequest);
        if (!response.isSuccess()) {
            throw new ChatMasterException(response.getErrTxt());
        }
        SparkTextUsage textUsage = response.getTextUsage();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(UUID.fastUUID().toString())
                .model(ChatModelEnum.SPARK.getValue()).modelVersion(SparkApiVersion.V2_0.getVersion())
                .content(response.getContent()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey(sparkClient.apiKey).usedTokens(Long.valueOf(textUsage.getTotalTokens()))
                .response(JSON.toJSONString(response))
                .build();
        return chatMessage;
    }

    /**
     * 更新token额度
     */
    private void updateToken(String appKey, Long usedTokens, String messageId) {
        openkeyMapper.updateUsedTokens(appKey, usedTokens);
        chatMessageService.updateMessageUsedTokens(messageId, usedTokens);
    }

    @Override
    public ChatMessageDTO getMessageByConverstationId(String conversationId) {
        ChatMessageDTO chatMessage = chatMessageService.getChatByMessageId(conversationId).getData();
        return chatMessage;
    }

    @Override
    public List<ChatMessageDTO> listMessageByConverstationId(Long userId, String conversationId) {
        User user = userMapper.selectById(userId);
        Boolean context = user.getContext();
        ChatMessageDTO chatMessage = chatMessageService.getChatByMessageId(conversationId).getData();
        List<ChatMessageDTO> chatMessages = new ArrayList<>();
        if (ValidatorUtil.isNotNull(context) && context) {
            chatMessages = chatMessageService.listChatMessage(chatMessage.getChatId()).getData();
        }
        chatMessages.add(chatMessage);
        return chatMessages;
    }

    /**
     * 校验账户
     *
     * @param command
     */
    @Override
    public void validateUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (ValidatorUtil.isNull(user)) {
            throw new ProhibitVisitException();
        }
        // 是否限制访问ChatMASTER
        Boolean flag = configService.getKeyValue(SysConfigConstants.CHAT_MASTER_ON_OFF);
        if (!flag) {
            return;
        }
        if (user.getNum() < 1) {
            throw new BusinessException("电量不足，请分享好友获取电量或开通会员");
        }
        // 扣电量
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.lambda().set(User::getNum, user.getNum() - 1).eq(BaseEntity::getId, user.getId());
        userMapper.update(null, uw);
    }

    @Override
    public void updateMessageStatus(String messageId, Integer status) {
        chatMessageService.updateMessageStatus(messageId, status);
    }

    @Override
    public void updateMessageUsedTokens(String messageId, Long usedTokens) {
        chatMessageService.updateMessageUsedTokens(messageId, usedTokens);
    }

}
