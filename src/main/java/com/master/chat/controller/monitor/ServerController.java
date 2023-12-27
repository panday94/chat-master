package com.master.chat.controller.monitor;

import com.master.chat.framework.web.entity.Server;
import com.master.common.api.ResponseInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    /**
     * 获取服务器监控信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping()
    public ResponseInfo getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ResponseInfo.success(server);
    }

}
