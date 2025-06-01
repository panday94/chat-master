package com.master.chat.api.controller.gpt;

import com.master.chat.api.controller.base.BaseController;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.core.pojo.vo.ChatVO;
import com.master.chat.core.service.IChatService;
import com.master.chat.sys.constant.SysLogTypeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  聊天摘要接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/gpt/chat" )
public class ChatController extends BaseController {
    @Autowired
    private IChatService chatService;

    /**
     * 查询聊天摘要分页列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/page" )
    @PreAuthorize("hasAuthority('gpt:chat:list')" )
    public ResponseInfo<IPageInfo<ChatVO>> pageChat(@RequestParam Map map) {
        return chatService.pageChat(new Query(map, true));
    }

    /**
     * 查询聊天摘要列表
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping("/list" )
    @PreAuthorize("hasAuthority('gpt:chat:list')" )
    public ResponseInfo<List<ChatVO>> listChat(@RequestParam Map map) {
        return chatService.listChat(new Query(map));
    }

    /**
     * 获取聊天摘要详细信息
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @GetMapping(value = "/{id}" )
    @PreAuthorize("hasAuthority('gpt:chat:query')" )
    public ResponseInfo<ChatVO> getChatById(@PathVariable("id" ) Long id) {
        return chatService.getChatById(id);
    }

    /**
     * 批量删除聊天摘要
     *
     * @author: Yang false
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}" )
    @PreAuthorize("hasAuthority('gpt:chat:remove')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeChatByIds(@PathVariable List<Long> ids) {
        return chatService.removeChatByIds(ids);
    }

}
