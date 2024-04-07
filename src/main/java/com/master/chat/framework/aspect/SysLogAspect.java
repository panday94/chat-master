package com.master.chat.framework.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.master.chat.framework.manger.AsyncManager;
import com.master.chat.framework.manger.factory.AsyncFactory;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.sys.pojo.entity.SysLog;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.utils.IPUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统日志，切面处理类
 * 使用：@Log(businessType = BusinessTypeEnum.SYSTEM, type = SysLogTypeConstant.SYSTEM, value = "添加系统设置") value默认时可不传
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.master.chat.common.annotation.Log)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            saveLog(joinPoint, result, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, Object result, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        if (ValidatorUtil.isNull(log)) {
            return;
        }
        UserDetail userDetail = JwtTokenUtils.getLoginUser();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader(Header.USER_AGENT.getValue()));
        String ip = IPUtil.getIpAddr(request);
        String urlStr = request.getRequestURL().toString();
        String domain = StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath());
        String parameter = getParameter(joinPoint.getArgs());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        methodName = className + StringPoolConstant.DOT + methodName + StringPoolConstant.LEFT_BRACKET + StringPoolConstant.RIGHT_BRACKET;
        SysLog sysLog = SysLog.builder()
                .sysUserId(ValidatorUtil.isNotNull(userDetail) ? userDetail.getId() : 0L)
                .username(ValidatorUtil.isNotNull(userDetail) ? userDetail.getUsername() : "游客")
                .ip(ip).domain(domain)
                .browser(userAgent.getBrowser().getName()).os(userAgent.getOs().getName())
                .method(methodName).requestMethod(request.getMethod()).uri(request.getRequestURI())
                .build();
        Stream<?> stream = ArrayUtils.isEmpty(joinPoint.getArgs()) ? Stream.empty() : Arrays.stream(joinPoint.getArgs());
        CommonCommand command = (CommonCommand) stream.filter(arg -> arg instanceof CommonCommand).findFirst().orElse(null);
        AsyncManager.me().execute(AsyncFactory.addSysLog(sysLog, parameter, result, time, log.value(), log.type(), log.businessType(), command));

    }

    /**
     * 根据方法和传入的参数获取请求参数
     *
     * @param args 请求参数
     * @return
     */
    private String getParameter(Object[] args) {
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest)
                        && !(arg instanceof HttpServletResponse)
                        && !(arg instanceof MultipartFile[])
                        && !(arg instanceof MultipartFile)
                        && !(arg instanceof Principal)))
                .collect(Collectors.toList());
        return logArgs.size() == 0 ? StringPoolConstant.EMPTY : JSON.toJSONString(logArgs);
    }

}
