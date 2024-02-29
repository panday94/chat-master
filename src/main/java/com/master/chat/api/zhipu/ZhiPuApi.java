package com.master.chat.api.zhipu;

import com.google.gson.Gson;
import com.master.chat.common.api.Query;
import com.master.chat.common.exception.BusinessException;
import com.master.chat.common.validator.ValidatorUtil;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.ConfigV3;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.httpclient.IHttpTransport;
import com.zhipu.oapi.core.httpclient.OkHttpTransport;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.core.token.TokenManager;
import com.zhipu.oapi.service.v3.*;
import com.zhipu.oapi.utils.OkHttps;
import com.zhipu.oapi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 智谱清言v3接口client
 *
 * @author: yang
 * @date: 2024/1/5
 * @version: 1.0.0
 * Copyright Ⓒ 2022 恒翔 Computer Corporation Limited All rights reserved.
 */
@Slf4j
public class ZhiPuApi {

    private ConfigV3 config;

    public ModelApiResponse invokeModelApi(ModelApiRequest request, Query query) {
        String paramMsg = validateParams(request);
        if (StringUtils.isNotEmpty(paramMsg)) {
            return new ModelApiResponse(-100, String.format("invalid param: %s", paramMsg));
        }
        if (Constants.invokeMethodAsync.equals(request.getInvokeMethod())) {
            return asyncInvoke(request, query);
        } else if (Constants.invokeMethodSse.equals(request.getInvokeMethod())) {
            return sseInvoke(request, query);
        } else if (Constants.invokeMethod.equals(request.getInvokeMethod())) {
            return invoke(request, query);
        }
        return null;
    }

    private ModelApiResponse invoke(ModelApiRequest request, Query query) {
        RawRequest rawReq = new RawRequest();
        // body
        // String requestTaskNo = IdUtil.getSnowflake(1, 2).nextIdStr();
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("prompt", request.getPrompt());
        if (ValidatorUtil.isNotNull(query.get("meta"))) {
            paramsMap.put("meta", query.get("meta"));
        }
        // incremental暂不支持配置，强制为true（增量返回）
        paramsMap.put("incremental", request.isIncremental());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("return_type", request.getReturnType());
        if (request.getRef() != null && !request.getRef().isEmpty()) {
            paramsMap.put("ref", request.getRef());
        }
        rawReq.setBody(paramsMap);
        rawReq.setReqUrl(baseModelUrl());
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("model", request.getModelId());
        pathParams.put("invoke_method", Constants.invokeMethod);
        rawReq.setPathParams(pathParams);

        // token
        String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
        rawReq.setToken(token);

        ModelApiResponse resp = new ModelApiResponse();
        try {
            RawResponse rawResp = this.getConfig().getHttpTransport().executePost(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            if (rawResp.getStatusCode() == 200) {
                Gson gson = new Gson();
                ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
                resp.setData(data);
            }
            return resp;
        } catch (Exception e) {
            log.error("invoke model fail!", e);
            resp.setCode(500);
            resp.setMsg("internal error");
        }
        return resp;
    }

    private ModelApiResponse sseInvoke(ModelApiRequest request, Query query) {
        RawRequest rawReq = new RawRequest();
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("prompt", request.getPrompt());
        if (ValidatorUtil.isNotNull(query.get("meta"))) {
            paramsMap.put("meta", query.get("meta"));
        }
        // incremental暂不支持配置，强制为true（增量返回）
        paramsMap.put("incremental", request.isIncremental());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("return_type", request.getReturnType());
        // sseformat, 用于兼容解决sse增量模式okhttpsse截取data:后面空格问题, [data: hello]。只在增量模式下使用sseFormat。
        if (request.isIncremental()) {
            paramsMap.put("sseFormat", ModelConstants.sseFormat);
        }
        rawReq.setBody(paramsMap);
        if (request.getRef() != null && !request.getRef().isEmpty()) {
            paramsMap.put("ref", request.getRef());
        }
        // sse info
        // TODO: listener放到config更合适
        rawReq.setSseListener(request.getSseListener());

        // url
        rawReq.setReqUrl(baseModelUrl());
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("model", request.getModelId());
        pathParams.put("invoke_method", Constants.invokeMethodSse);
        rawReq.setPathParams(pathParams);

        // token
        String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
        rawReq.setToken(token);

        ModelApiResponse resp = new ModelApiResponse();
        try {
            RawResponse rawResp = this.getConfig().getHttpTransport().sseExecute(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            Gson gson = new Gson();
            ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
            resp.setData(data);
            return resp;
        } catch (Exception e) {
            log.error("sse invoke model fail!", e);
            resp.setCode(500);
            resp.setMsg("internal error");
        }

        return resp;
    }

    private ModelApiResponse asyncInvoke(ModelApiRequest request, Query query) {
        ModelApiResponse resp = new ModelApiResponse();
        try {
            RawRequest rawReq = new RawRequest();
            Map<String, Object> paramsMap = new HashMap();
            paramsMap.put("request_id", request.getRequestId());
            paramsMap.put("prompt", request.getPrompt());
            if (ValidatorUtil.isNotNull(query.get("meta"))) {
                paramsMap.put("meta", query.get("meta"));
            }
            paramsMap.put("temperature", request.getTemperature());
            paramsMap.put("top_p", request.getTopP());
            rawReq.setBody(paramsMap);
            rawReq.setReqUrl(baseModelUrl());
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put("model", request.getModelId());
            pathParams.put("invoke_method", Constants.invokeMethodAsync);
            paramsMap.put("return_type", request.getReturnType());
            if (request.getRef() != null && !request.getRef().isEmpty()) {
                paramsMap.put("ref", request.getRef());
            }
            rawReq.setPathParams(pathParams);
            rawReq.setConfigV3(config);
            String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
            rawReq.setToken(token);

            RawResponse rawResp = this.getConfig().getHttpTransport().executePost(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            if (rawResp.getStatusCode() == 200) {
                Gson gson = new Gson();
                ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
                resp.setData(data);
            }
            return resp;
        } catch (Exception e) {
            log.error("invoke model fail", e);
            resp.setCode(500);
            resp.setMsg("internal error, please check log");
            return resp;
        }
    }

    public QueryModelResultResponse queryModelResult(QueryModelResultRequest request) {
        QueryModelResultResponse resp = new QueryModelResultResponse();

        try {
            RawRequest rawReq = new RawRequest();
            rawReq.setConfigV3(config);
            rawReq.setReqUrl(baseQueryResultUrl());
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put("task_id", request.getTaskId());
            rawReq.setPathParams(pathParams);
            String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
            rawReq.setToken(token);
            RawResponse rawResp = this.getConfig().getHttpTransport().executeGet(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            if (resp.getCode() == 200) {
                Gson gson = new Gson();
                ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
                resp.setData(data);
            }
            return resp;
        } catch (Exception e) {
            log.error("call query result fail", e);
            resp.setCode(500);
            resp.setMsg("internal error, please check log");
            return resp;
        }
    }

    private String validateParams(ModelApiRequest request) {
        if (request == null) {
            return "request can not be null";
        }
        // 目前仅支持异步和sse接口
        if (!Constants.invokeMethodAsync.equals(request.getInvokeMethod())
                && !Constants.invokeMethodSse.equals(request.getInvokeMethod()) && !Constants.invokeMethod.equals(request.getInvokeMethod())) {
            return "invalid invoke method";
        }
        //
        if (request.getPrompt() == null || request.getPrompt().size() == 0) {
            return "prompt can not be empty";
        }
        return null;
    }

    public ConfigV3 getConfig() {
        return this.config;
    }

    public void setConfig(ConfigV3 config) {
        this.config = config;
    }

    private String baseModelUrl() {
        return this.config.isDevMode() ? "https://test-maas.aminer.cn/stage-api/paas/v3/model-api/{model}/{invoke_method}" : "https://open.bigmodel.cn/api/paas/v3/model-api/{model}/{invoke_method}";
    }

    private String baseQueryResultUrl() {
        return this.config.isDevMode() ? "https://test-maas.aminer.cn/stage-api/paas/v3/model-api/-/async-invoke/{task_id}" : "https://open.bigmodel.cn/api/paas/v3/model-api/-/async-invoke/{task_id}";
    }

    public static final class Builder {
        private ConfigV3 config = new ConfigV3();

        public Builder(String apiSecretKey) {
            this.config.setApiSecretKey(apiSecretKey);
        }

        public Builder(String apiKey, String apiSecret) {
            this.config.setApiKey(apiKey);
            this.config.setApiSecret(apiSecret);
            this.config.setDisableTokenCache(false);
        }

        public ZhiPuApi.Builder disableTokenCache() {
            this.config.setDisableTokenCache(true);
            return this;
        }

        public ZhiPuApi.Builder tokenCache(ICache cache) {
            this.config.setCache(cache);
            return this;
        }

        public ZhiPuApi.Builder tokenExpire(int expireMillis) {
            this.config.setExpireMillis(expireMillis);
            return this;
        }

        public ZhiPuApi.Builder httpTransport(IHttpTransport httpTransport) {
            this.config.setHttpTransport(httpTransport);
            return this;
        }

        public ZhiPuApi.Builder devMode(boolean devMode) {
            this.config.setDevMode(devMode);
            return this;
        }

        private void initCache(ConfigV3 config) {
            if (config.getCache() != null) {
                GlobalTokenManager.setTokenManager(new TokenManager(config.getCache()));
            } else {
                ICache cache = LocalCache.getInstance();
                GlobalTokenManager.setTokenManager(new TokenManager(cache));
            }

        }

        private void initHttpTransport(ConfigV3 config) {
            if (config.getHttpTransport() == null) {
                if (config.getRequestTimeOut() > 0) {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.create((long) config.getRequestTimeOut(), config.getTimeOutTimeUnit())));
                } else {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.defaultClient));
                }
            }

        }

        public ZhiPuApi build() {
            ZhiPuApi zhiPuApi = new ZhiPuApi();
            zhiPuApi.setConfig(this.config);
            this.initCache(this.config);
            this.initHttpTransport(this.config);
            return zhiPuApi;
        }
    }

}
