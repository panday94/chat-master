package com.master.chat.gpt.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.fastjson.JSON;
import com.master.chat.api.base.config.LocalCache;
import com.master.chat.api.base.entity.ChatData;
import com.master.chat.api.base.enums.ChatContentEnum;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.base.listener.SSEListener;
import com.master.chat.api.openai.OpenAiStreamClient;
import com.master.chat.api.openai.entity.chat.ChatCompletion;
import com.master.chat.api.openai.entity.chat.OpenAiMessage;
import com.master.chat.api.qianwen.QianWenClient;
import com.master.chat.api.qianwen.listener.QianWenSseListener;
import com.master.chat.api.wenxin.WenXinClient;
import com.master.chat.api.wenxin.constant.ModelE;
import com.master.chat.api.wenxin.entity.ImageResponse;
import com.master.chat.api.wenxin.entity.ImagesBody;
import com.master.chat.api.wenxin.entity.MessageItem;
import com.master.chat.api.xfyun.SparkClient;
import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.listener.SparkSseListener;
import com.master.chat.api.zhipu.ZhiPuClient;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.gpt.service.IGptService;
import com.master.chat.gpt.service.SseService;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.StatusEnum;
import com.master.common.exception.BusinessException;
import com.master.common.exception.ErrorException;
import com.master.common.utils.ApplicationContextUtil;
import com.master.common.validator.ValidatorUtil;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v3.ModelApiRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
            log.info("[{}]结束连接", uid);
            LocalCache.CACHE.remove(uid);
        });
        //超时回调
        sseEmitter.onTimeout(() -> {
            log.info("[{}]连接超时", uid);
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
        if (ValidatorUtil.isNull(model)) {
            throw new BusinessException("模型已经不存在啦，请切换模型进行回复～");
        }
        if (StatusEnum.DISABLED.getValue().equals(model.getStatus())) {
            throw new BusinessException("该模型已被禁用，请切换模型进行回复～");
        }
        String prompt = chatMessage.getContent();
        String version = model.getVersion();
        ChatModelEnum modelEnum = ChatModelEnum.getEnum(chatMessage.getModel());
        SseEmitter sseEmitter = createSse(user.getUid());
        if (sseEmitter == null) {
            log.info("聊天消息推送失败uid:[{}],没有创建连接，请重试。", user.getUid());
            throw new BusinessException("聊天消息推送失败uid:[{}],没有创建连接，请重试。~");
        }
        // 校验用户
        gptService.validateUser(user.getId());
        List<ChatMessageDTO> chatMessages = gptService.listMessageByConverstationId(user.getContext(), conversationId);
        if (ValidatorUtil.isNullIncludeArray(chatMessages)) {
            throw new BusinessException("消息发送失败");
        }
        // ChatGPT、文心一言、智谱清言统一在SSEListener中处理流式返回，通义千问与讯飞星火单独处理
        Boolean flag;
        try {
            switch (modelEnum) {
                case CHAT_GPT:
                    flag = sseByOpenAi(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, prompt, version);
                    break;
                case WENXIN:
                    flag = sseByWenXin(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, prompt, version);
                    break;
                case QIANWEN:
                    flag = sseByQianWen(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, prompt, version);
                    break;
                case SPARK:
                    flag = sseBySpark(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, prompt, version);
                    break;
                case ZHIPU:
                    flag = sseByZhiPu(sseEmitter, response, chatMessages, chatMessage.getChatId(), conversationId, prompt, version);
                    break;
                default:
                    throw new BusinessException("未知的模型类型，功能未接入。");
            }
            Integer status = ChatStatusEnum.SUCCESS.getValue();
            if (flag) {
                status = ChatStatusEnum.ERROR.getValue();
            }
            gptService.updateMessageStatus(conversationId, status);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException();
        }
    }

    /**
     * ChatGPT流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean sseByOpenAi(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                                Long chatId, String conversationId, String prompt, String version) {
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
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

    /**
     * 文心一言流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean sseByWenXin(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                                Long chatId, String conversationId, String prompt, String version) {
        if (isDraw(prompt)) {
            return imageByWenXin(sseEmitter, response, chatId, conversationId, prompt);
        }
        List<MessageItem> messages = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            messages.add(MessageItem.builder().role(v.getRole()).content(v.getContent()).build());
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.WENXIN.getValue(), version);
        ModelE model = ValidatorUtil.isNotNull(version) ? ModelE.getEnum(version) : ModelE.ERNIE_Bot_turbo;
        if (ValidatorUtil.isNull(model)) {
            throw new BusinessException("文心大模型不存在，请检查模型名称。");
        }
        wenXinClient.streamChat(messages, sseListener, model);
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

    /**
     * 文心一言文生图
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean imageByWenXin(SseEmitter sseEmitter, HttpServletResponse response, Long chatId, String conversationId, String prompt) {
        // 通知客户端返回为图片
        ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                .parentMessageId(conversationId)
                .role(ChatRoleEnum.ASSISTANT.getValue()).contentType(ChatContentEnum.IMAGE.getValue()).build();
        response.getWriter().write(JSON.toJSONString(chatData));
        response.getWriter().flush();

        // 请求生成图片 并返回base64信息
        ImagesBody body = ImagesBody.builder().prompt(prompt).build();
        ResponseInfo<ImageResponse> imageResponse = wenXinClient.image(body);
        if (!imageResponse.isSuccess()) {
            throw new BusinessException(imageResponse.getMsg());
        }
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        ImageResponse image = imageResponse.getData();
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(image.getId()).parentMessageId(conversationId)
                .model(ChatModelEnum.WENXIN.getValue()).modelVersion(ModelE.STABLE_DIFFUSION_XL.getLabel())
                .content(JSON.toJSONString(image.getData())).contentType(ChatContentEnum.IMAGE.getValue()).role(ChatRoleEnum.ASSISTANT.getValue())
                .status(ChatStatusEnum.SUCCESS.getValue()).usedTokens(image.getUsage().getTotalTokens())
                .build();
        ApplicationContextUtil.getBean(IChatMessageService.class).saveChatMessage(chatMessage);
        chatData.setContent(image.getData());
        response.getWriter().write("\n" + JSON.toJSONString(chatData));
        response.getWriter().flush();
        return false;
    }

    /**
     * 智谱清言流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean sseByZhiPu(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                               Long chatId, String conversationId, String prompt, String version) {
        List<ModelApiRequest.Prompt> prompts = new ArrayList<>();
        chatMessages.stream().forEach(v -> {
            prompts.add(new ModelApiRequest.Prompt(v.getRole(), v.getContent()));
        });
        SSEListener sseListener = new SSEListener(response, sseEmitter, chatId, conversationId, ChatModelEnum.ZHIPU.getValue(), version);
        ModelApiRequest request = new ModelApiRequest();
        request.setModelId(ValidatorUtil.isNotNull(version) ? version : Constants.ModelChatGLM6B);
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
        Query query = new Query();
        zhiPuClient.streamChat(request, query, sseListener);
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

    /**
     * 通议千问流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean sseByQianWen(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                                 Long chatId, String conversationId, String prompt, String version) {
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
        Semaphore semaphore = new Semaphore(0);
        QianWenSseListener sseListener = new QianWenSseListener(response, semaphore, chatId, conversationId, version);
        gen.streamCall(param, sseListener);
        semaphore.acquire();
        return sseListener.getError();
    }

    /**
     * 讯飞星火流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean sseBySpark(SseEmitter sseEmitter, HttpServletResponse response, List<ChatMessageDTO> chatMessages,
                               Long chatId, String conversationId, String prompt, String version) {
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
        sparkClient.streamChat(sparkRequest, sseListener);
        sseListener.getCountDownLatch().await();
        return sseListener.getError();
    }

    private static final String[] drawingWords = {"画画", "作画", "画图", "绘画", "描绘"};
    private static final String[] drawingInstructions = {"请画", "画一", "画个",};

    /**
     * 判断是否需要画画
     *
     * @param prompt 输入内容
     * @return
     */
    private Boolean isDraw(String prompt) {
        // 检查是否有明确的画画词汇
        for (String word : drawingWords) {
            if (prompt.contains(word)) {
                return true;
            }
        }
        // 检查是否有指导性的画画语句
        for (String instruction : drawingInstructions) {
            if (prompt.contains(instruction)) {
                return true;
            }
        }
        // 检查是否有描述画画的动作的词或其他相关名词
        // 这里只是一个示例，实际上需要更复杂的词性标注和语境分析
        if (prompt.contains("画") || prompt.contains("绘画")) {
            return true;
        }
        return false;
    }

}
