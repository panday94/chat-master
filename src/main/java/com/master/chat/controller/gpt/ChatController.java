package com.master.chat.controller.gpt;

import com.master.chat.framework.base.BaseController;
import com.master.chat.gpt.pojo.command.ChatCommand;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.gpt.service.IChatService;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  聊天摘要接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
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
     * 新增聊天摘要
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PostMapping
    @PreAuthorize("hasAuthority('gpt:chat:save')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveChat(@Validated @RequestBody ChatCommand command) {
        return chatService.saveChat(command);
    }

    /**
     * 修改聊天摘要
     *
     * @author: Yang
     * @date: 2023-04-28
     * @version: 1.0.0
     */
    @PutMapping
    @PreAuthorize("hasAuthority('gpt:chat:update')" )
    @Log(type = SysLogTypeConstant.DEFAULT, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateChat(@Validated @RequestBody ChatCommand command) {
        return chatService.updateChat(command);
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
