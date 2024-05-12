package com.master.chat.framework.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.common.api.CommonCommand;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.utils.IPUtil;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 请求日志及访问限制
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {
    @Autowired
    private HttpServletRequest request;
    /**
     * 是否记录所有日志到数据库中
     */
    private static final Boolean IS_ADD_MYSQL = false;

    @Pointcut("execution(* com.master.chat..controller..*.*(..))")
    public void requestApi() {
    }

    @Before("requestApi()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        addOperaterInfo(joinPoint.getArgs());
    }

    @After("requestApi()")
    public void doAfter() throws Throwable {
    }

    @Around("requestApi()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        Object[] args = joinPoint.getArgs();
        String parameter = getParameter(args);
        saveSyslog(joinPoint, parameter, result, time);
        log.info(getRequestParams(), getRequestArgs(parameter, time));
        return result;
    }

    /**
     * 添加操作人信息
     *
     * @param args
     */
    private void addOperaterInfo(Object[] args) {
        if (ValidatorUtil.isNullIncludeArray(args)) {
            return;
        }
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        CommonCommand command = (CommonCommand) stream.filter(arg -> arg instanceof CommonCommand).findFirst().orElse(null);
        if (ValidatorUtil.isNull(command)) {
            return;
        }
        UserDetail userDetail = JwtTokenUtils.getLoginUser();
        if (ValidatorUtil.isNotNull(userDetail)) {
            command.setOperaterId(userDetail.getId());
            command.setOperater(userDetail.getUsername());
        }
    }

    /**
     * 构建系统请求日志信息
     *
     * @param parameter 请求参数
     * @param result    返回结果
     * @param time      请求耗时
     */
    private void saveSyslog(ProceedingJoinPoint joinPoint, String parameter, Object result, long time) {
        if (!IS_ADD_MYSQL) {
            return;
        }
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            UserDetail userDetail = JwtTokenUtils.getLoginUser();
            UserAgent userAgent = UserAgentUtil.parse(request.getHeader(Header.USER_AGENT.getValue()));
            String ip = IPUtil.getIpAddr(request);
            String urlStr = request.getRequestURL().toString();
            String domain = StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath());
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            methodName = className + StringPoolConstant.DOT + methodName + StringPoolConstant.LEFT_BRACKET + StringPoolConstant.RIGHT_BRACKET;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求日志参数
     *
     * @param args 请求参数
     * @return
     */
    private String getRequestParams() {
        // 构建成一条长 日志，避免并发下日志错乱
        StringBuilder requestLog = new StringBuilder(300);
        requestLog.append("\n");
        requestLog.append("请求IP：{}\n");
        requestLog.append("请求路径：{}\n");
        requestLog.append("请求方式：{}\n");
        requestLog.append("请求参数：{}\n");
        requestLog.append("请求耗时：{}ms");
        return requestLog.toString();
    }

    /**
     * 获取请求日志参数
     *
     * @param args 请求参数
     * @return
     */
    private Object[] getRequestArgs(String parameter, Long time) {
        List<Object> responseArgs = new ArrayList<>();
        responseArgs.add(IPUtil.getIpAddr(request));
        responseArgs.add(request.getRequestURI());
        responseArgs.add(request.getMethod());
        responseArgs.add(parameter);
        responseArgs.add(time);
        return responseArgs.toArray();
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
