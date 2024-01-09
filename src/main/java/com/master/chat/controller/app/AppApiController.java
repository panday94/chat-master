package com.master.chat.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.master.chat.common.constant.RedisConstants;
import com.master.chat.common.constant.SmsConstant;
import com.master.chat.common.util.AliyunSMSUtil;
import com.master.chat.common.util.RedisUtils;
import com.master.chat.gpt.constant.BaseConfigConstant;
import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.vo.AgreementVO;
import com.master.chat.gpt.pojo.vo.AppConfigVO;
import com.master.chat.gpt.pojo.vo.AssistantVO;
import com.master.chat.gpt.service.*;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.enums.IntEnum;
import com.master.common.enums.ResponseEnum;
import com.master.common.enums.StatusEnum;
import com.master.common.utils.RandomUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取小程序基础信息接口
 *
 * @author: yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController
@RequestMapping("/app/api")
public class AppApiController {
    @Autowired
    private IBaseConfigService baseConfigService;
    @Autowired
    private IAssistantTypeService assistantTypeService;
    @Autowired
    private IAssistantService assistantService;
    @Autowired
    private IAgreementService contentService;
    @Autowired
    private IGptService gptService;
    @Autowired
    private SseService sseService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送短信
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @PostMapping("/sms/send")
    public ResponseInfo sendSmsCode(@RequestBody Query query) {
        query = new Query(query);
        String tel = query.getTel();
        if (IntEnum.ELEVEN.getValue() != tel.length()) {
            return ResponseInfo.businessFail("请输入正确手机号码");
        }
        String code = RandomUtil.randomNumbers(6);
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", code);
        String key = RedisConstants.TEL_CODE + tel;
        if (ValidatorUtil.isNotNull(redisUtils.get(key))) {
            return ResponseInfo.customizeError(ResponseEnum.REPEAT_REQUEST_SMS);
        }
        AliyunSMSUtil.sendSms(tel, SmsConstant.AUTHCODE_TEMPLATE, map);
        redisUtils.set(key, code, RedisConstants.FIVE_MINUTES);
        return ResponseInfo.success();
    }

    /**
     * 获取app配置信息
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/home/config")
    public ResponseInfo getHomeConfig() {
        JSONObject baseInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.BASE_INFO).getData();
        JSONObject extraInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.EXTRA_INFO).getData();
        JSONObject appInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.APP_INFO).getData();
        JSONObject wxInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.WX_INFO).getData();
        Query query = new Query();
        query.put("status", StatusEnum.ENABLED.getValue());
        List<AssistantVO> assistants = assistantService.listAssistant(query).getData();
        AssistantVO assistant = assistants.stream().filter(v -> StatusEnum.ENABLED.getValue().equals(v.getMainModel())).findFirst().get();
        AppConfigVO appConfig = AppConfigVO.builder()
                .baseInfo(baseInfo).extraInfo(extraInfo)
                .appInfo(appInfo).wxInfo(wxInfo)
                .mainAssistant(assistant).assistants(assistants)
                .build();
        return ResponseInfo.success(appConfig);
    }

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
        if (ValidatorUtil.isNull(contents)) {
            return ResponseInfo.success();
        }
        return ResponseInfo.success(contents.get(0));
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
     * 随机获取Ai助手
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/assistant/random")
    public ResponseInfo listAssistant() {
        Query query = new Query();
        query.put("status", StatusEnum.ENABLED.getValue());
        query.put("size", 3);
        return assistantService.listAssistantByApp(new Query(query));
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
