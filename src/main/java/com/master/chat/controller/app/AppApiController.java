package com.master.chat.controller.app;

import com.master.chat.comm.util.RedisUtils;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.vo.AgreementVO;
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
    private IAssistantTypeService assistantTypeService;
    @Autowired
    private IAssistantService assistantService;
    @Autowired
    private IAgreementService contentService;
    @Autowired
    private IGptService gptService;
    @Autowired
    private ICombService combService;
    @Autowired
    private RedisUtils redisUtils;


    /**
     * 获取协议信息
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/content/agreement/{type}")
    public ResponseInfo getAgreement(@PathVariable("type") Integer type) {
        Query query = new Query();
        query.put("type", type);
        query.put("status", StatusEnum.ENABLED.getValue());
        List<AgreementVO> contents = contentService.listContent(query).getData();
        if (ValidatorUtil.isNullIncludeArray(contents)) {
            return ResponseInfo.success();
        }
        return ResponseInfo.success(contents.get(0));
    }

    /**
     * 获取系统内容信息
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/content/{type}")
    public ResponseInfo listContent(@PathVariable("type") Integer type) {
        Query query = new Query();
        query.put("type", type);
        query.put("status", StatusEnum.ENABLED.getValue());
        return contentService.listContent(query);
    }

    /**
     * 获取内容详情
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/content/detail/{id}")
    public ResponseInfo getContent(@PathVariable("id") Long id) {
        return contentService.getContentById(id);
    }

    /**
     * 获取Ai分类
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/assistant/type")
    public ResponseInfo listAssistantType() {
        Query query = new Query();
        query.put("status", StatusEnum.ENABLED.getValue());
        return assistantTypeService.listAssistantType(query);
    }

    /**
     * 获取Ai助手
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/assistant")
    public ResponseInfo listAssistantByType(@RequestParam Map map) {
        Query query = new Query(map, true);
        query.put("status", StatusEnum.ENABLED.getValue());
        return assistantService.listAssistantByApp(query);
    }

    /**
     * 获取Ai助手
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/assistant/{id}")
    public ResponseInfo getAssistantById(@PathVariable Long id) {
        return assistantService.getAssistantById(id);
    }

    /**
     * 随机获取Ai助手
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/assistant/random")
    public ResponseInfo listAssistant(@RequestParam(required = false) Map map) {
        Query query = new Query();
        query.put("status", StatusEnum.ENABLED.getValue());
        query.put("size", ValidatorUtil.isNotNull(map.get("size")) ? BaseAssert.getParamInt(map, "size") : 3);
        return assistantService.listAssistantByApp(new Query(query));
    }

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
