package com.master.chat.gpt.service;

import com.master.chat.framework.security.UserDetail;
import com.master.chat.common.api.ResponseInfo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 * @date 2023-04-08
 */
public interface SseService {

    /**
     * 创建SSE
     *
     * @param uid
     * @return
     */
    SseEmitter createSse(String uid);

    /**
     * 关闭SSE
     *
     * @param uid
     */
    void closeSse(String uid);

    /**
     * 客户端发送消息到服务端
     *
     * @param uid
     * @param chatRequest
     */
    void sseChat(UserDetail user, String conversationId, HttpServletResponse response);

}
