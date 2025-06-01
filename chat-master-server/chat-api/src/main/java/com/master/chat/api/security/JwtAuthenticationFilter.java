package com.master.chat.api.security;

import com.alibaba.fastjson.JSON;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.AuthConstant;
import com.master.chat.common.constant.HttpConstant;
import com.master.chat.common.constant.RedisConstants;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.AccountEnum;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.framework.util.ApplicationContextUtil;
import com.master.chat.framework.util.RedisUtils;
import com.master.chat.framework.validator.ValidatorUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * 登录认证过滤器
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, MediaType.ALL.getType());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, MediaType.ALL.getType());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, MediaType.ALL.getType());
        response.setHeader(HttpHeaders.CACHE_CONTROL, HttpConstant.NO_CACHE);
        RedisUtils redisUtil = ApplicationContextUtil.getBean(RedisUtils.class);
        // 从Header中拿到token
        String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = null;
        Boolean flag = false;
        ResponseInfo responseInfo = ResponseInfo.error();
        try {
            authentication = JwtTokenUtils.getAuthenticationeFromToken(token);
        } catch (ValidateException e) {
            responseInfo = ResponseInfo.unauthorized(e.getMsg());
        } catch (UnsupportedJwtException e) {
            responseInfo = ResponseInfo.unauthorized("不支持的JWT格式");
        } catch (MalformedJwtException e) {
            responseInfo = ResponseInfo.unauthorized("非JWT格式");
        } catch (SignatureException e) {
            responseInfo = ResponseInfo.unauthorized("签名错误");
        } catch (IllegalArgumentException e) {
            responseInfo = ResponseInfo.unauthorized();
        } catch (ExpiredJwtException e) {
            responseInfo = ResponseInfo.unauthorized("token已过期");
        } catch (Exception e) {
            e.printStackTrace();
            responseInfo = ResponseInfo.unauthorized("解析token失败");
        }
        UserDetail userDetail = JwtTokenUtils.getUserDetail(authentication);
        // 判断redis中是否存在该token
        if (ValidatorUtil.isNotNull(authentication)) {
            String key = AccountEnum.ADMIN.getValue().equals(userDetail.getRole()) ? RedisConstants.LOGIN_TOKEN_ADMIN_KEY : RedisConstants.LOGIN_TOKEN_USER_KEY;
            if (ValidatorUtil.isNull(redisUtil.get(key + userDetail.getId() + StringPoolConstant.COLON + userDetail.getSessionId()))) {
                flag = true;
                responseInfo = ResponseInfo.unauthorized("token已失效，请重新登录");
            }
        }
        // 未登录或者token失效返回
        if (ValidatorUtil.isNull(authentication) || flag) {
            response.getWriter().print(JSON.toJSONString(responseInfo));
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }
        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}