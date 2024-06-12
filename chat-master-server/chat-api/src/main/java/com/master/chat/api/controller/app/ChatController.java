package com.master.chat.api.controller.app;

import com.master.chat.api.base.BaseAppController;
import com.master.chat.client.enums.ChatStatusEnum;
import com.master.chat.client.model.command.ChatCommand;
import com.master.chat.client.service.GptService;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.gpt.service.IChatService;
import com.master.chat.llm.base.service.LLMService;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.framework.validator.ValidatorUtil;
import com.master.chat.framework.validator.base.BaseAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 对话接口
 *
 * @author: Yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController(value = "appChatController")
@RequestMapping("/v1/chat")
public class ChatController extends BaseAppController {
    @Autowired
    private IChatService chatService;
    @Autowired
    private IChatMessageService chatMessageService;
    @Autowired
    private GptService gptService;
    @Autowired
    private LLMService llmService;

    /**
     * 获取聊天列表
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @GetMapping()
    public ResponseInfo listChat(@RequestParam Map map) {
        map.put("userId", getLoginUser().getId());
        return chatService.listChat(new Query(map));
    }

    /**
     * 删除聊天列表
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @DeleteMapping("/{chatNumber}")
    public ResponseInfo deleteChat(@PathVariable("chatNumber") String chatNumber) {
        return chatService.removeChatByChatNumber(chatNumber);
    }

    /**
     * 获取聊天内容列表
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @GetMapping("/message")
    public ResponseInfo listChatMessage(@RequestParam Map map) {
        BaseAssert.isBlankOrNull(map.get("chatNumber"), "缺少会话标识");
        Query query = new Query(map);
        query.put("status", ChatStatusEnum.SUCCESS.getValue());
        return chatMessageService.listChatMessage(query);
    }

    /**
     * 获取聊天内容
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @GetMapping("/message/{messageId}")
    public ResponseInfo listChatMessageById(@PathVariable String messageId) {
        return chatMessageService.getChatByMessageId(messageId);
    }

    /**
     * 删除聊天内容
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @DeleteMapping("/message/{messageId}")
    public ResponseInfo removeChatMessageById(@PathVariable String messageId) {
        return chatMessageService.removeChatMessageByMessageId(messageId);
    }

    /**
     * 创建对话
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @PostMapping()
    public ResponseInfo<ChatVO> saveChat(@RequestBody ChatCommand command) {
        command.setUserId(getUserId());
        return chatService.saveSSEChat(command);
    }

    /**
     * 发送消息
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @PostMapping("/message")
    public ResponseInfo sendMessage(@Validated @RequestBody ChatCommand command) {
        command.setUserId(getUserId());
        command = gptService.validateGptCommand(command);
        return ResponseInfo.success(gptService.chatMessage(command));
    }

    /**
     * 创建sse连接
     *
     * @param headers
     * @return
     */
    @GetMapping("/sse/create")
    public SseEmitter createConnect(@RequestHeader(name = "uid") String uid) {
        return llmService.createSse(uid);
    }

    /**
     * 关闭连接
     *
     * @param headers
     */
    @GetMapping("/sse/close")
    public void closeConnect(@RequestHeader(name = "uid") String uid) {
        llmService.closeSse(uid);
    }

    /**
     * 对话响应
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @GetMapping("/completions")
    public void completions(String conversationId, HttpServletResponse response) {
        llmService.sseChat(response, getLoginUser().getUid(), conversationId);
    }

    /**
     * 同步响应
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @PostMapping("/completions/sync")
    public ResponseInfo syncCompletions(@RequestBody ChatCommand command) {
        command.setUserId(getUserId());
        return ResponseInfo.success(llmService.chat(command));
    }

}
