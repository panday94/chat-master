package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.gpt.service.IChatMessageService;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.common.annotation.Log;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  对话消息接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/chat-message" )
public class ChatMessageController extends BaseController {
    @Autowired
    private IChatMessageService chatMessageService;

    /**
     * 查询对话消息分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:chat:message:list')" )
    public ResponseInfo<IPageInfo<ChatMessageVO>> pageChatMessage(@RequestParam Map map) {
        return chatMessageService.pageChatMessage(new Query(map, true));
    }

    /**
     * 查询对话消息列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:chat:message:list')" )
    public ResponseInfo<List<ChatMessageVO>> listChatMessage(@RequestParam Map map) {
        return chatMessageService.listChatMessage(new Query(map));
    }

    /**
     * 获取对话消息详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:chat:message:query')" )
    public ResponseInfo<ChatMessageVO> getChatMessageById(@PathVariable("id" ) Long id) {
        return chatMessageService.getChatMessageById(id);
    }

    /**
     * 批量删除对话消息
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:chat:message:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeChatMessageByIds(@PathVariable List<Long> ids) {
        return chatMessageService.removeChatMessageByIds(ids);
    }

}
