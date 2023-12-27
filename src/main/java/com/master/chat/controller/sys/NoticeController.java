package com.master.chat.controller.sys;

import com.master.chat.framework.base.BaseController;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.NoticeCommand;
import com.master.chat.sys.service.INoticeService;
import com.master.chat.sys.pojo.vo.NoticeVO;
import com.master.common.annotation.Log;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.BusinessTypeEnum;
import com.master.common.enums.IntegerEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统通知接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Autowired
    private INoticeService noticeService;

    /**
     * 获取系统通知分页数据
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:notice:list')")
    public ResponseInfo<IPageInfo<NoticeVO>> pageNotice(@RequestParam Map map) {
        map.put("type", IntegerEnum.ONE.getValue());
        return noticeService.pageNotice(new Query(map, true));
    }

    /**
     * 根据登录账号获取系统通知数据
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/read/page")
    public ResponseInfo<IPageInfo<NoticeVO>> pageNoticeBySysUser(@RequestParam Map map) {
        map.put("sysUserId", getSysUserId());
        return noticeService.pageNoticeBySysUser(new Query(map, true));
    }

    /**
     * 根据登录账号获取系统未读数量
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/unread/count")
    public ResponseInfo<Integer> getUnreadCount() {
        return noticeService.getUnreadCount(getSysUserId());
    }

    /**
     * 获取系统通知详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:notice:query')")
    public ResponseInfo<NoticeVO> getNoticeById(@PathVariable Long id) {
        return noticeService.getNoticeById(id);
    }

    /**
     * 添加系统通知
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:notice:save')")
    @Log(type = SysLogTypeConstant.NOTICE, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo saveNotice(@Validated @RequestBody NoticeCommand command) {
        return noticeService.saveNotice(command);
    }

    /**
     * 修改系统通知
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:notice:update')")
    @Log(type = SysLogTypeConstant.NOTICE, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updateNotice(@Validated @RequestBody NoticeCommand command) {
        return noticeService.updateNotice(command);
    }

    /**
     * 已读系统通知
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/read")
    public ResponseInfo readNotice(@RequestBody NoticeCommand command) {
        return noticeService.readNotice(command.getId(), getSysUserId());
    }

    /**
     * 一键已读系统通知
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping("/read/all")
    public ResponseInfo allReadNotice() {
        return noticeService.allReadNotice(getSysUserId());
    }

    /**
     * 删除系统通知
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:notice:remove')")
    @Log(type = SysLogTypeConstant.NOTICE, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeNoticeById(@PathVariable List<Long> ids) {
        return noticeService.removeNoticeByIds(ids);
    }

}
