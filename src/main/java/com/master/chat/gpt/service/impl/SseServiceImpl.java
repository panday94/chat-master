package com.master.chat.gpt.service.impl;

import com.master.chat.api.base.config.LocalCache;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.xfyun.SparkClient;
import com.master.chat.api.xfyun.constant.SparkApiVersion;
import com.master.chat.api.xfyun.entity.SparkMessage;
import com.master.chat.api.xfyun.entity.request.SparkRequest;
import com.master.chat.api.xfyun.listener.SparkSseListener;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.gpt.service.IGptService;
import com.master.chat.gpt.service.SseService;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-04-08
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {
    private static final String[] drawingWords = {"画画", "作画", "画图", "绘画", "描绘"};
    private static final String[] drawingInstructions = {"请画", "画一", "画个",};
    private static SparkClient sparkClient;
    private final IGptService gptService;

    @Autowired
    public SseServiceImpl(IGptService gptService, SparkClient sparkClient) {
        this.gptService = gptService;
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
                                .data(null)
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
        List<ChatMessageDTO> chatMessages = gptService.listMessageByConverstationId(user.getId(), conversationId);
        if (ValidatorUtil.isNullIncludeArray(chatMessages)) {
            throw new BusinessException("消息发送失败");
        }
        // ChatGPT、文心一言统一在SSEListener中处理流式返回，通义千问与讯飞星火\智谱清言单独处理
        Boolean flag = false;
        try {
            switch (modelEnum) {
                case SPARK:
                    flag = sseBySpark(response, sseEmitter, chatMessages, user.getUid(), chatMessage.getChatId(), conversationId, prompt, version);
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
            flag = true;
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            flag = true;
            throw new ErrorException();
        } finally {
            if (flag) {
                gptService.restoreNum(user.getId());
            }
        }
    }


    /**
     * 讯飞星火流式输出
     *
     * @param uid
     * @param prompt
     * @return
     */
    @SneakyThrows
    private Boolean sseBySpark(HttpServletResponse response, SseEmitter sseEmitter, List<ChatMessageDTO> chatMessages,
                               String uid, Long chatId, String conversationId, String prompt, String version) {
        if (ValidatorUtil.isNull(sparkClient.appid)) {
            throw new BusinessException("未加载到密钥信息");
        }
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
