package com.master.chat.api.zhipu;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.master.chat.common.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.image.CreateImageRequest;
import com.zhipu.oapi.service.v4.image.ImageApiResponse;
import com.zhipu.oapi.service.v4.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智谱清言client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 MasterComputer Corporation Limited All rights reserved.
 */
@Slf4j
@NoArgsConstructor(force = true)
public class ZhiPuClient {

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "master-%d";
    @NotNull
    @Getter
    @Setter
    private String appKey;
    @NotNull
    @Getter
    @Setter
    private String appSecret;
    @Getter
    private ClientV4 clientV4;

    private ZhiPuClient(Builder builder) {
        if (StrUtil.isBlank(builder.appKey)) {
            throw new BusinessException("构造错误: accessToken不能为空");
        }
        appKey = builder.appKey;
        appSecret = builder.appSecret;

        this.clientV4 = new ClientV4.Builder(appKey, appSecret).build();
    }

    /**
     * 同步响应
     *
     * @param request
     * @param eventSourceListener
     */
    public ModelApiResponse chat(ChatCompletionRequest request) {
        // 插件调用
//        request = functionCall(request);
        request.setStream(Boolean.FALSE);
        request.setInvokeMethod(Constants.invokeMethod);
        request.setRequestId(String.format(requestIdTemplate, System.currentTimeMillis()));
        ModelApiResponse invokeModelApiResp = this.clientV4.invokeModelApi(request);
        try {
            return invokeModelApiResp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 插件调用
    private ChatCompletionRequest functionCall(ChatCompletionRequest request) {
        // 函数调用参数构建部分
        List<ChatTool> chatToolList = new ArrayList<>();
        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.FUNCTION.value());
        ChatFunctionParameters chatFunctionParameters = new ChatFunctionParameters();
        chatFunctionParameters.setType("object");
        Map<String, Object> properties = new HashMap<>();
        properties.put("location", new HashMap<String, Object>() {{
            put("type", "string");
            put("description", "城市，如：北京");
        }});
        properties.put("unit", new HashMap<String, Object>() {{
            put("type", "string");
            put("enum", new ArrayList<String>() {{
                add("celsius");
                add("fahrenheit");
            }});
        }});
        chatFunctionParameters.setProperties(properties);
        ChatFunction chatFunction = ChatFunction.builder()
                .name("get_weather")
                .description("Get the current weather of a location")
                .parameters(chatFunctionParameters)
                .build();
        chatTool.setFunction(chatFunction);
        chatToolList.add(chatTool);
        request.setTools(chatToolList);
        request.setToolChoice("auto");
        return request;
    }

    /**
     * 流式响应
     *
     * @param request
     * @param query
     */
    public Boolean streamChat(HttpServletResponse response, ChatCompletionRequest request, Long chatId, String parentMessageId, String version) {
        request.setStream(Boolean.TRUE);
        request.setRequestId(String.format(requestIdTemplate, System.currentTimeMillis()));
        ModelApiResponse sseModelApiResp = this.clientV4.invokeModelApi(request);
        ZhiPuApi zhiPuApi = new ZhiPuApi(response, chatId, parentMessageId, version);
        return zhiPuApi.streamChat(sseModelApiResp);
    }

    /**
     * 生成图片
     */
    private void testCreateImage() {
        CreateImageRequest createImageRequest = new CreateImageRequest();
        createImageRequest.setModel(Constants.ModelCogView);
//        createImageRequest.setPrompt("画一个温顺可爱的小狗");
        ImageApiResponse imageApiResponse = this.clientV4.createImage(createImageRequest);
        System.out.println("imageApiResponse:" + JSON.toJSONString(imageApiResponse));
    }


    /**
     * 构造
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull String appKey;
        private @NotNull String appSecret;

        public Builder() {
        }

        public Builder appKey(@NotNull String val) {
            appKey = val;
            return this;
        }

        public Builder appSecret(@NotNull String val) {
            appSecret = val;
            return this;
        }

        public ZhiPuClient build() {
            return new ZhiPuClient(this);
        }
    }

}
