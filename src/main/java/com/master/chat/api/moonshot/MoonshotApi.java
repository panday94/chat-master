package com.master.chat.api.moonshot;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.master.chat.api.base.entity.ChatData;
import com.master.chat.api.base.enums.ChatContentEnum;
import com.master.chat.api.base.enums.ChatModelEnum;
import com.master.chat.api.base.enums.ChatRoleEnum;
import com.master.chat.api.moonshot.constant.MoonshotApiConstant;
import com.master.chat.api.moonshot.constant.MoonshotApiVersion;
import com.master.chat.api.moonshot.entity.ChatCompletionResponse;
import com.master.chat.api.moonshot.entity.ChatCompletionStreamResponse;
import com.master.chat.api.moonshot.entity.Usage;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.ApplicationContextUtil;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.gpt.enums.ChatStatusEnum;
import com.master.chat.gpt.pojo.command.ChatMessageCommand;
import com.master.chat.gpt.service.IChatMessageService;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class MoonshotApi {
    public static String MOONSHOT_API_KEY = "";
    public static String SYSTEM_CONTENT = "你是 Kimi，由 Moonshot AI 提供的人工智能助手，你更擅长中文和英文的对话。你会为用户提供安全，有帮助，准确的回答。同时，你会拒绝一些涉及恐怖主义，种族歧视，黄色暴力等问题的回答。Moonshot AI 为专有名词，不可翻译成其他语言。";

    private HttpServletResponse response;
    private StringBuffer output = new StringBuffer();
    private Long chatId;
    private String parentMessageId;
    private String conversationId;
    private String finishReason = "stop";
    private String version;
    private Boolean error;
    private String errTxt;

    /**
     * 流式响应
     *
     * @param request
     * @param query
     */
    public MoonshotApi(HttpServletResponse response, Long chatId, String parentMessageId, String version) {
        this.response = response;
        this.chatId = chatId;
        this.parentMessageId = parentMessageId;
        this.version = version;
        if (response == null) {
            log.error("客户端非sse推送");
            return;
        }
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        log.info("月之暗面建立sse连接...");
    }

    /**
     * 流失回答
     *
     * @param request
     */
    public Boolean streamChat(Response response) {
        ChatCompletionStreamResponse chatMessageAccumulator = mapStreamToAccumulator(response)
                .doOnNext(accumulator -> {
                    {
//                            if (isFirst.getAndSet(false)) {
//                                System.out.print("Response: ");
//                            }
//                            if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
//                                String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
//                                System.out.println("tool_calls: " + jsonString);
//                            }
                        if (accumulator.getChoices() != null && accumulator.getChoices().get(0).getDelta().getContent() != null) {
                            log.info("月之暗面返回，数据：{}", accumulator.getChoices().get(0).getDelta().getContent());
                            output.append(accumulator.getChoices().get(0).getDelta().getContent()).toString();
                            // 向客户端发送信息
                            output();
                        }
                    }
                }).doOnComplete(System.out::println).lastElement().blockingGet();
        this.conversationId = chatMessageAccumulator.getId();
        Usage usage = chatMessageAccumulator.getChoices().get(0).getUsage();
        log.info("月之暗面回数据结束了:{}", JSON.toJSONString(chatMessageAccumulator));
        ChatMessageCommand chatMessage = ChatMessageCommand.builder().chatId(chatId).messageId(conversationId).parentMessageId(parentMessageId)
                .model(ChatModelEnum.MOONSHOT.getValue()).modelVersion(version)
                .content(output.toString()).contentType(ChatContentEnum.TEXT.getValue()).role(ChatRoleEnum.ASSISTANT.getValue()).finishReason(finishReason)
                .status(ChatStatusEnum.SUCCESS.getValue()).appKey("").usedTokens(Long.valueOf(usage.getTotalTokens()))
                .build();
        ApplicationContextUtil.getBean(IChatMessageService.class).saveChatMessage(chatMessage);
        return false;

    }

    private void output() {
        try {
            String text = output.toString();
            ChatData chatData = ChatData.builder().id(conversationId).conversationId(conversationId)
                    .parentMessageId(parentMessageId)
                    .role(ChatRoleEnum.ASSISTANT.getValue()).content(text).build();
            response.getWriter().write(ValidatorUtil.isNull(text) ? JSON.toJSONString(chatData) : "\n" + JSON.toJSONString(chatData));
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("消息错误", e);
            throw new ErrorException();
        }
    }

    public static Flowable<ChatCompletionStreamResponse> mapStreamToAccumulator(Response response) {
        return Flowable.create(emitter -> {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                emitter.onError(new RuntimeException("Response body is null"));
                return;
            }
            String line;
            while ((line = responseBody.source().readUtf8Line()) != null) {
                if (line.startsWith("data:")) {
                    line = line.substring(5);
                    line = line.trim();
                }
                if (Objects.equals(line, "[DONE]")) {
                    emitter.onComplete();
                    return;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Gson gson = new Gson();
                ChatCompletionStreamResponse streamResponse = gson.fromJson(line, ChatCompletionStreamResponse.class);
                emitter.onNext(streamResponse);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 对话
     *
     * @param question
     */
    public static ResponseInfo<ChatCompletionResponse> chat(String prompt) {
        return sendChatCompletionsCommon(prompt, MoonshotApiConstant.CHAT_COMPLETION_URL, ChatCompletionResponse.class);
    }

    /**
     * 计算token
     *
     * @param question
     */
    public static ResponseInfo<ChatCompletionResponse> countToken(String prompt) {
        return sendChatCompletionsCommon(prompt, MoonshotApiConstant.ESTIMATE_TOKEN_COUNT_URL, ChatCompletionResponse.class);
    }

    /**
     * 发送对话请求
     *
     * @param prompt
     * @param url
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> ResponseInfo<T> sendChatCompletionsCommon(String prompt, String url, Class<T> clz) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", MoonshotApiVersion.API_MODEL_8K);
        paramMap.put("temperature", 0.2);
        paramMap.put("max_tokens", 4096);
        List<Map<String, String>> messageList = new ArrayList<>();
        Map<String, String> sysMsgMap = new HashMap<>();
        sysMsgMap.put("role", "system");
        Map<String, String> userMsgMap = new HashMap<>();
        userMsgMap.put("role", "user");
        String sysContent = SYSTEM_CONTENT;
        String userContent = "推荐100家企业清单";
        if (StringUtils.isNotBlank(prompt)) {
            userContent = prompt;
        }
        sysMsgMap.put("content", sysContent);
        userMsgMap.put("content", userContent);
        messageList.add(sysMsgMap);
        messageList.add(userMsgMap);
        paramMap.put("messages", messageList);
        String result = null;
        try {
            result = HttpUtil.createPost(url).addHeaders(headerMap).form(paramMap).execute().body();
            System.out.println(result);
            return ResponseInfo.success(JSON.parseObject(result, clz));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseInfo.error();
        }
    }

    /**
     * 获取文件列表
     *
     * @param ->
     * @return
     **/
    public static String filesList() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        String result = null;
        try {
            result = HttpUtil.createGet(MoonshotApiConstant.UPLOAD_FILES_URL).addHeaders(headerMap).execute().body();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     **/
    public static String uploadFile(MultipartFile file) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("purpose", "file-extract");
        String result = null;
        try {
            result = HttpUtil.createPost(MoonshotApiConstant.UPLOAD_FILES_URL).addHeaders(headerMap).form(paramMap).execute().body();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据API 文件id查看单个文件
     *
     * @param fileId 文件id
     * @return
     **/
    public static String fileInfo(String fileId) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        String result = null;
        try {
            result = HttpUtil.createGet(MoonshotApiConstant.UPLOAD_FILES_URL + "/" + fileId).addHeaders(headerMap).execute().body();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据文件id查看文件内容
     *
     * @param fileId 文件id
     * @return
     **/
    public static String fileContent(String fileId) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        String result = null;
        try {
            result = HttpUtil.createGet(MoonshotApiConstant.UPLOAD_FILES_URL + "/" + fileId + "/content").addHeaders(headerMap).execute().body();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据文件id删除文件
     *
     * @param fileId 文件id
     * @return
     **/
    public static String delfile(String fileId) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        String result = null;
        try {
            result = HttpRequest.delete(MoonshotApiConstant.UPLOAD_FILES_URL + "/" + fileId).addHeaders(headerMap).execute().body();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    static HashMap<String, String> assistantMap = new HashMap<>();

    public static JSONObject httpOutputToPage(String clientId, String question, String fileId, String goOnContent) {
        JSONObject jsonObject = null;
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", "Bearer " + MOONSHOT_API_KEY);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", MoonshotApiVersion.API_MODEL_8K);
        paramMap.put("temperature", 0.2);
        paramMap.put("max_tokens", 4096);
        List<Map<String, String>> messageList = new ArrayList<>();
        Map<String, String> sysMsgMap = new HashMap<>();
        sysMsgMap.put("role", "system");
        Map<String, String> userMsgMap = new HashMap<>();
        userMsgMap.put("role", "user");
        String sysContent = SYSTEM_CONTENT;
        String userContent = "园区招商推荐100家企业清单";
        sysMsgMap.put("content", sysContent);
        if (StringUtils.isNotBlank(question)) {
            userContent = question;
        }
        userMsgMap.put("content", userContent);
        messageList.add(sysMsgMap);
        messageList.add(userMsgMap);
        paramMap.put("messages", messageList);
        if (StringUtils.isNotBlank(fileId)) {
            Map<String, Object> fileContent = JSONUtil.toBean(fileContent(fileId), Map.class);
            if (fileContent != null && fileContent.size() > 0 && StringUtils.isNotBlank((String) fileContent.get("content"))) {
                Map<String, String> fileMsgMap = new HashMap<>();
                fileMsgMap.put("role", "system");
                fileMsgMap.put("content", (String) fileContent.get("content"));
                messageList.add(fileMsgMap);
            }
        }
        if (StringUtils.isNotBlank(goOnContent) && StringUtils.isNotBlank(assistantMap.get(goOnContent))) {
            Map<String, String> filegoOnMsgMap = new HashMap<>();
            filegoOnMsgMap.put("role", "system");
            filegoOnMsgMap.put("content", assistantMap.get(goOnContent));
            messageList.add(filegoOnMsgMap);
        }
//		assistantMap.put("role", "assistant");
//		assistantMap.put("content", "");
//		StringBuilder temp = new StringBuilder();
        String result = null;
        // 发起异步请求
        try {
            result = HttpUtil.createPost(MoonshotApiConstant.CHAT_COMPLETION_URL).addHeaders(headerMap).form(paramMap).execute().body();
            //从6开始 因为有 data: 这个前缀 占了6个字符所以 0 + 6 = 6
            jsonObject = JSON.parseObject(result);
            if (jsonObject != null && jsonObject.getJSONArray("choices") != null && jsonObject.getJSONArray("choices").size() > 0) {
                result = jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            }
            if (StringUtils.isNotBlank(result)) {
                //SSE协议默认是以两个\n换行符为结束标志 需要在进行一次转义才能成功发送给前端
                //将结果汇总起来
                assistantMap.put(clientId, result);
            }
        } catch (Exception e) {
            System.out.println("___--------" + result);
            e.printStackTrace();
        }
        System.out.println("___--------" + result);
        System.out.println("___--------" + jsonObject.toJSONString());
        return jsonObject;
    }


}
