package com.master.chat.gpt.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.master.chat.api.base.config.LocalCache;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.listener.SSEListener;
import com.master.chat.api.openai.OpenAiStreamClient;
import com.master.chat.api.openai.entity.chat.OpenAiMessage;
import com.master.chat.api.qianwen.QianWenClient;
import com.master.chat.api.xfyun.SparkClient;
import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.listener.SparkSseListener;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.zhipu.ZhiPuClient;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IGptService;
import com.master.chat.gpt.service.SseService;
import com.master.chat.api.openai.entity.chat.ChatCompletion;
import com.master.chat.api.openai.exception.BaseException;
import com.master.chat.api.qianwen.listener.QianWenSseListener;
import com.master.chat.api.wenxin.WenXinClient;
import com.master.chat.api.wenxin.constant.ModelE;
import com.master.chat.api.wenxin.entity.MessageItem;
import com.master.common.exception.BusinessException;
import com.master.common.validator.ValidatorUtil;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v3.ModelApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-04-08
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {
    private final IGptService gptService;
    private static OpenAiStreamClient openAiStreamClient;
    private static WenXinClient wenXinClient;
    private static ZhiPuClient zhiPuClient;
    private static QianWenClient qianWenClient;
    private static SparkClient sparkClient;

    @Autowired
    public SseServiceImpl(IGptService gptService, OpenAiStreamClient openAiStreamClient, WenXinClient wenXinClient,
                          ZhiPuClient zhiPuClient, QianWenClient qianWenClient, SparkClient sparkClient) {
        this.gptService = gptService;
        SseServiceImpl.openAiStreamClient = openAiStreamClient;
        SseServiceImpl.wenXinClient = wenXinClient;
        SseServiceImpl.zhiPuClient = zhiPuClient;
        SseServiceImpl.qianWenClient = qianWenClient;
        SseServiceImpl.sparkClient = sparkClient;
    }

    @Override
    public SseEmitter createSse(String uid) {
        //默认30秒超时,设置为0L则永不超时
        SseEmitter sseEmitter = new SseEmitter(0L);
        //完成后回调
        sseEmitter.onCompletion(() -> {
            log.info("[{}]结束连接...................", uid);
            LocalCache.CACHE.remove(uid);
        });
        //超时回调
        sseEmitter.onTimeout(() -> {
            log.info("[{}]连接超时...................", uid);
        });
        //异常回调
        sseEmitter.onError(
                throwable -> {
                    try {
                        log.info("[{}]连接异常,{}", uid, throwable.toString());
                        sseEmitter.send(SseEmitter.event()
                                .id(uid)
                                .name("发生异常！")
                                .data(OpenAiMessage.builder().content("发生异常请重试！").build())
                                .reconnectTime(3000));
                        LocalCache.CACHE.put(uid, sseEmitter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        try {
            sseEmitter.send(SseEmitter.event().reconnectTime(5000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalCache.CACHE.put(uid, sseEmitter);
        log.info("[{}]创建sse连接成功！", uid);
        return sseEmitter;
    }

    @Override
    public void closeSse(String uid) {
        SseEmitter sse = (SseEmitter) LocalCache.CACHE.get(uid);
        if (sse != null) {
            sse.complete();
            //移除
            LocalCache.CACHE.remove(uid);
        }
    }

    @Override
    public void sseChat(UserDetail user, String conversationId, HttpServletResponse response) {
        ChatMessageDTO chatMessage = gptService.getMessageByConverstationId(conversationId);
        ModelVO model = gptService.getModel(chatMessage.getModel()).getData();
        String version = model.getVersion();
        ChatModelEnum modelEnum = ChatModelEnum.getEnum(chatMessage.getModel());
        SseEmitter sseEmitter = createSse(user.getUid());
        if (sseEmitter == null) {
            log.info("聊天消息推送失败uid:[{}],没有创建连接，请重试。", user.getUid());
            throw new BaseException("聊天消息推送失败uid:[{}],没有创建连接，请重试。~");
        }
        // 校验用户
        gptService.validateUser(user.getId());
        List<ChatMessageDTO> chatMessages = gptService.listMessageByConverstationId(user.getContext(), conversationId);
        if (ValidatorUtil.isNullIncludeArray(chatMessages)) {
            throw new BusinessException("消息发送失败");
        }
        // ChatGPT、文心一言、智谱清言统一在SSEListener中处理流式返回，通义千问与讯飞星火单独处理
        switch (modelEnum) {
            case CHAT_GPT:
                sseByOpenAi(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, version);
                break;
            case WENXIN:
                sseByWenXin(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, version);
                break;
            case QIANWEN:
                sseByQianWen(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, version);
                break;
            case SPARK:
                sseBySpark(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, version);
                break;
            case ZHIPU:
                sseByZhiPu(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, version);
                break;
            default:
                throw new BusinessException("未知的模型类型，功能未接入");
        }

    }

    /**
     * ChatGPT流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    private void sseByOpenAi(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                             Long chatId, String conversationId, String version) {
        List<OpenAiMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            OpenAiMessage currentMessage = OpenAiMessage.builder().content(v.getContent()).role(v.getRole()).build();
            messages.add(currentMessage);
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.CHAT_GPT.getValue(), version);
        ChatCompletion completion = ChatCompletion
                .builder()
                .messages(messages)
                .model(ValidatorUtil.isNotNull(version) ? version : ChatCompletion.Model.GPT_3_5_TURBO_0613.getName())
                .build();
        openAiStreamClient.streamChatCompletion(completion, sseListener);
        try {
            sseListener.getCountDownLatch().await();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.SUCCESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.ERROR.getValue());
            throw new RuntimeException(e);
        }
    }

    /**
     * 文心一言流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    private void sseByWenXin(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                             Long chatId, String conversationId, String version) {
        List<MessageItem> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            messages.add(MessageItem.builder().role(v.getRole()).content(v.getContent()).build());
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.WENXIN.getValue(), version);
        ModelE modelE = ValidatorUtil.isNotNull(version) ? ModelE.getEnum(version) : ModelE.ERNIE_Bot_turbo;
        wenXinClient.streamChat(messages, sseListener, modelE);
        try {
            sseListener.getCountDownLatch().await();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.SUCCESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.ERROR.getValue());
            throw new RuntimeException(e);
        }
    }

    /**
     * 通议千问流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    private void sseByQianWen(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                              Long chatId, String conversationId, String version) {
        MessageManager msgManager = new MessageManager(20);
        chatMessages.stream().forEach(v -> {
            msgManager.add(Message.builder().role(v.getRole()).content(v.getContent()).build());
        });
        Generation gen = new Generation();
        QwenParam param = QwenParam.builder().apiKey(qianWenClient.getAppKey())
                .model(ValidatorUtil.isNotNull(version) ? version : Generation.Models.QWEN_TURBO)
                .messages(msgManager.get())
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .enableSearch(true)
                .build();
        try {
            Semaphore semaphore = new Semaphore(0);
            QianWenSseListener sseListener = new QianWenSseListener(response, semaphore, chatId, conversationId, version);
            gen.streamCall(param, sseListener);
            semaphore.acquire();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.SUCCESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.ERROR.getValue());
            throw new RuntimeException(e);
        }
    }

    /**
     * 讯飞星火流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    private void sseBySpark(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                            Long chatId, String conversationId, String version) {
        List<SparkMessage> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            SparkMessage currentMessage = new SparkMessage(v.getRole(), v.getContent());
            messages.add(currentMessage);
        });
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
        sparkRequest.getHeader().setAppId(sparkClient.appid);
        SparkSseListener sseListener = new SparkSseListener(sparkRequest, response, chatId, conversationId, version);
        try {
            sparkClient.streamChat(sparkRequest, sseListener);
            sseListener.getCountDownLatch().await();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.SUCCESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.ERROR.getValue());
            throw new RuntimeException(e);
        }
    }

    /**
     * 智谱清言流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    private void sseByZhiPu(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                            Long chatId, String conversationId, String version) {
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            ModelApiRequest.Prompt prompt = new ModelApiRequest.Prompt(v.getRole(), v.getContent());
            prompts.add(prompt);
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.ZHIPU.getValue(), version);
        ModelApiRequest request = new ModelApiRequest();
        request.setModelId(Constants.ModelChatGLM6B);
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
        zhiPuClient.streamChat(request, sseListener);
        try {
            sseListener.getCountDownLatch().await();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.SUCCESS.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            gptService.updateMessageStatus(conversationId, ChatStatusEnum.ERROR.getValue());
            throw new RuntimeException(e);
        }
    }

}
