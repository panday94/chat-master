package com.master.chat.controller.app;

import com.master.chat.comm.util.RedisUtils;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.service.*;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.common.validator.base.BaseAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 获取小程序基础信息接口
 *
 * @author: Yang
 * @date: 2023/5/4
 * @version: 1.0.0

 */
@RestController
@RequestMapping("/app/api")
public class AppApiController {
    @Autowired
    private IGptService gptService;
    @Autowired
    private ICombService combService;

    /**
     * 获取会员套餐信息
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/comb/{type}")
    public ResponseInfo listComb(@PathVariable Integer type) {
        Query query = new Query();
        query.put("status", StatusEnum.ENABLED.getValue());
        query.put("type", type);
        return combService.listComb(query);
    }

    /**
     * 提问
     *
     * @author: Yang
     * @date: 2023/5/5
     * @version: 1.0.0
     */
    @PostMapping("/chat")
    public ResponseInfo sendMessage(@RequestBody GptCommand command) {
        command.setApi(true);
        return gptService.chat(command);
    }

}
