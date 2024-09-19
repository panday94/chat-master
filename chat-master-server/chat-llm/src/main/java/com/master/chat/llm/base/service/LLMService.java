package com.master.chat.llm.base.service;

import com.master.chat.client.enums.ChatModelEnum;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatCommand;
import com.master.chat.client.model.command.ChatMessageCommand;
import com.master.chat.client.model.dto.ChatMessageDTO;
import com.master.chat.client.service.GptService;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.IntegerEnum;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.llm.base.entity.ChatData;
import com.master.chat.llm.base.exception.LLMException;
import com.master.chat.llm.base.service.impl.*;
import com.master.chat.llm.chatglm.ChatGLMClient;
import com.master.chat.llm.internlm.InternlmClient;
import com.master.chat.llm.locallm.coze.CozeClient;
import com.master.chat.llm.locallm.langchain.LangchainClient;
import com.master.chat.llm.locallm.ollama.OllamaClient;
import com.master.chat.llm.moonshot.MoonshotClient;
import com.master.chat.llm.openai.OpenAiClient;
import com.master.chat.llm.openai.OpenAiStreamClient;
import com.master.chat.llm.spark.SparkClient;
import com.master.chat.llm.tongyi.TongYiClient;
import com.master.chat.llm.wenxin.WenXinClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 大模型 接口实现
 *
 * @author: Yang
 * @date: 2024/5/30
 * @version: 1.1.7
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@Service
public class LLMService {
    private static final String[] drawingWords = {"画画", "作画", "画图", "绘画", "描绘"};
    private static final String[] drawingInstructions = {"请画", "画一", "画个",};
    private static OpenAiClient openAiClient;
    private static OpenAiStreamClient openAiStreamClient;
    private static WenXinClient wenXinClient;
    private static ChatGLMClient chatGLMClient;
    private static TongYiClient tongYiClient;
    private static SparkClient sparkClient;
    private static MoonshotClient moonshotClient;
    private static InternlmClient internlmClient;
    private static LangchainClient langchainClient;
    private static OllamaClient ollamaClient;
    private static CozeClient cozeClient;
    private final GptService gptService;

    @Autowired
    public LLMService(GptService gptService, OpenAiClient openAiClient, OpenAiStreamClient openAiStreamClient, WenXinClient wenXinClient,
                      ChatGLMClient chatGLMClient, TongYiClient tongYiClient, SparkClient sparkClient, MoonshotClient moonshotClient,
                      InternlmClient internlmClient, LangchainClient langchainClient, OllamaClient ollamaClient, CozeClient cozeClient) {
        this.gptService = gptService;
        LLMService.openAiClient = openAiClient;
        LLMService.openAiStreamClient = openAiStreamClient;
        LLMService.wenXinClient = wenXinClient;
        LLMService.chatGLMClient = chatGLMClient;
        LLMService.tongYiClient = tongYiClient;
        LLMService.sparkClient = sparkClient;
        LLMService.moonshotClient = moonshotClient;
        LLMService.internlmClient = internlmClient;
        LLMService.langchainClient = langchainClient;
        LLMService.ollamaClient = ollamaClient;
        LLMService.cozeClient = cozeClient;
    }

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
                                .data(ChatData.builder().content("发生异常请重试！").build())
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

    public void closeSse(String uid) {
        SseEmitter sse = (SseEmitter) LocalCache.CACHE.get(uid);
        if (sse != null) {
            sse.complete();
            //移除
            LocalCache.CACHE.remove(uid);
        }
    }

    /**
     * 根据模型获取大模型实现
     *
     * @param model
     * @return
     */
    private ModelService getLLM(ChatModelEnum model) {
        switch (model) {
            case OPENAI:
                return new OpenAIServiceImpl(openAiClient, openAiStreamClient);
            case WENXIN:
                return new WenXinServiceImpl(gptService, wenXinClient);
            case TONGYI:
                return new TongYiServiceImpl(tongYiClient);
            case SPARK:
                return new SparkServiceImpl(sparkClient);
            case CHATGLM:
                return new ChatGLMServiceImpl(chatGLMClient);
            case INTERNLM:
                return new InternLMServiceImpl(internlmClient);
            case MOONSHOT:
                return new MoonshotServiceImpl(moonshotClient);
            case LOCALLM:
                return new LocalLMServiceImpl(langchainClient, ollamaClient, cozeClient, gptService);
            default:
                return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo chat(ChatCommand command) {
        command = gptService.validateGptCommand(command);
        ChatMessageDTO userMessage = new ChatMessageDTO();
        ChatMessageCommand chatMessage;
        try {
            Long chatId = gptService.saveChat(command);
            List<ChatMessageDTO> messages = gptService.saveChatMessage(command, chatId, command.getConversationId());
            userMessage = messages.get(messages.size() - 1);
            if (ValidatorUtil.isNullIncludeArray(messages)) {
                throw new BusinessException("消息发送失败");
            }
            ChatModelEnum modelEnum = ChatModelEnum.getEnum(command.getModel());
            String version = command.getModelVersion();
            chatMessage = getLLM(modelEnum).chat(messages, isDraw(command.getPrompt()), chatId, version);
        } catch (LLMException e) {
            gptService.updateMessageStatus(userMessage.getMessageId(), IntegerEnum.THREE.getValue());
            return ResponseInfo.success();
        }
        chatMessage.setParentMessageId(userMessage.getMessageId());
        gptService.saveChatMessage(chatMessage);
        gptService.updateMessageStatus(userMessage.getMessageId(), IntegerEnum.TWO.getValue());
        return ResponseInfo.success(DozerUtil.convertor(chatMessage, ChatMessageDTO.class));
    }


    public void sseChat(HttpServletResponse response, Boolean isWs, String uid, String conversationId) {
        ChatMessageDTO chatMessage = gptService.getMessageByConverstationId(conversationId);
        String prompt = chatMessage.getContent();
        String version = chatMessage.getModelVersion();
        ChatModelEnum modelEnum = ChatModelEnum.getEnum(chatMessage.getModel());
        SseEmitter sseEmitter = createSse(uid);
        if (sseEmitter == null) {
            log.info("聊天消息推送失败uid:[{}],没有创建连接，请重试。", uid);
            throw new BusinessException("聊天消息推送失败uid:[{}],没有创建连接，请重试。~");
        }
        List<ChatMessageDTO> chatMessages = gptService.listMessageByConverstationId(uid, conversationId);
        if (ValidatorUtil.isNullIncludeArray(chatMessages)) {
            throw new BusinessException("消息发送失败");
        }
        Boolean error = false;
        Integer status = ChatStatusEnum.SUCCESS.getValue();
        try {
            // ChatGPT、文心一言统一在SSEListener中处理流式返回，通义千问与讯飞星火\智谱清言单独处理
            error = getLLM(modelEnum).streamChat(response, sseEmitter, chatMessages, isWs, isDraw(prompt), chatMessage.getChatId(), conversationId, prompt, version, uid);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException();
        } finally {
            gptService.updateMessageStatus(conversationId, error ? ChatStatusEnum.ERROR.getValue() : status);
            if (error) {
                throw new ErrorException("模型输出失败");
            }
        }
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
