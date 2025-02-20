package com.master.chat.llm.doubao;

import com.master.chat.llm.doubao.listener.SSEListener;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChunk;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionResult;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * 豆包client
 * 文档地址：https://www.volcengine.com/docs/82379/1099455
 *
 * @author: Yang
 * @date: 2025/2/16
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
public class DouBaoClient {

    @Getter
    @Setter
    private String apiKey;

    public DouBaoClient() {
    }

    public DouBaoClient(String apiKey) {
        this.apiKey = apiKey;
    }

    private DouBaoClient(DouBaoClient.Builder builder) {
        apiKey = builder.apiKey;
    }

    /**
     * 同步响应
     *
     * @param request
     * @param eventSourceListener
     */
    public ChatCompletionResult chat(ChatCompletionRequest request) {
        ArkService service = ArkService.builder().apiKey(apiKey).build();
        ChatCompletionResult invokeModelApiResp = service.createChatCompletion(request);
        try {
            return invokeModelApiResp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 流式响应
     *
     * @param request
     * @param query
     */
    public Boolean streamChat(HttpServletResponse response, ChatCompletionRequest request, Long chatId, String parentMessageId, String version, String uid, Boolean isWs) {
        ArkService service = ArkService.builder().apiKey(apiKey).build();
        SSEListener sseListener = new SSEListener(response, chatId, parentMessageId, version, uid, isWs);
        Flowable<ChatCompletionChunk> chunks =  service.streamChatCompletion(request);
        Boolean flag = sseListener.streamChat(chunks);
        // shutdown service
        service.shutdownExecutor();
        return flag;
    }


    /**
     * 构造
     *
     * @return
     */
    public static DouBaoClient.Builder builder() {
        return new DouBaoClient.Builder();
    }

    public static final class Builder {
        private String apiKey;

        public Builder() {
        }

        public DouBaoClient.Builder apiKey(String val) {
            apiKey = val;
            return this;
        }

        public DouBaoClient build() {
            return new DouBaoClient(this);
        }
    }

}
