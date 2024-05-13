package com.master.chat.framework.interceptor;

import com.alibaba.fastjson.JSON;
import com.master.chat.common.annotation.RepeatSubmit;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.utils.ApplicationContextUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0

 */
@Component
public abstract class BaseRepeatSubmitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    ResponseInfo responseInfo = ResponseInfo.error(annotation.message());
                    ApplicationContextUtil.renderString(response, JSON.toJSONString(responseInfo));
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request
     * @param annotation 防重复提交注解注解
     * @return 是否重复提交
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);

}
