package com.master.chat.framework.security;

import com.master.chat.sys.service.impl.ResourceServiceImpl;
import com.master.chat.common.constant.AuthConstant;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.ValidateException;
import com.master.chat.common.utils.ApplicationContextUtil;
import com.master.chat.common.validator.ValidatorUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

/**
 * JWT工具类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
public class JwtTokenUtils {

    /**
     * 密钥
     */
    private static final String SECRET = "chatmaster888";

    /**
     * 有效期7天
     */
    public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000;

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public static UserDetail getLoginUser() {
        return getUserDetail(getAuthentication());
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public static String generateToken(Authentication authentication) {
        UserDetail userDetail = getUserDetail(authentication);
        Map<String, Object> claims = new HashMap<>(16);
        claims.put("id", userDetail.getId());
        claims.put("uid", userDetail.getUid());
        claims.put("sessionId", userDetail.getSessionId());
        claims.put("username", userDetail.getUsername());
        claims.put("admind", userDetail.getAdmind());
        claims.put("role", userDetail.getRole());
        return Jwts.builder()
                .setClaims(claims)
                // 用户名写入标题
                .setSubject(userDetail.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param request
     * @return
     */
    public static Authentication getAuthenticationeFromToken(String token) {
        if (ValidatorUtil.isNull(token)) {
            throw new ValidateException("token不能为空");
        }
        Authentication authentication = getAuthentication();
        if (ValidatorUtil.isNotNull(authentication)) {
            return authentication;
        }
        //解析 Token
        UserDetail userDetail = getUserDetailFromToken(token);
        // 得到 权限（角色）
        //Set<String> permissions = JSON.parseObject(JSON.toJSONString(claims.get("authorities")), Set.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (1 == userDetail.getRole()) {
            Set<String> permissions = ApplicationContextUtil.getBean(ResourceServiceImpl.class).listButtonResourceBySysUser(userDetail.getId(), userDetail.getUsername()).getData();
            Optional.ofNullable(permissions).ifPresent(v -> v.stream().forEach(d -> authorities.add(new SimpleGrantedAuthority(d))));
        }
        // 返回验证令牌
        return new UsernamePasswordAuthenticationToken(userDetail, null, authorities);
    }

    /**
     * 从令牌中获取用户信息
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static UserDetail getUserDetailFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Long id = Long.valueOf(claims.get("id").toString());
        String sessionId = Optional.ofNullable(claims.get("sessionId")).map(v -> v.toString()).orElse(StringPoolConstant.EMPTY);
        String username = Optional.ofNullable(claims.get("username")).map(v -> v.toString()).orElse(StringPoolConstant.EMPTY);
        String uid = Optional.ofNullable(claims.get("uid")).map(v -> v.toString()).orElse(StringPoolConstant.EMPTY);
        Boolean admind = Optional.ofNullable(claims.get("admind")).map(v -> Boolean.valueOf(v.toString())).orElse(false);
        Integer role = Optional.ofNullable(claims.get("role")).map(v -> Integer.valueOf(v.toString())).orElse(0);
        return new UserDetail(id, uid, sessionId, username, admind, role);
    }


    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        // 解析 Token
        Claims claims = Jwts.parser()
                // 验签
                .setSigningKey(SECRET)
                // 去掉 Bearer
                .parseClaimsJws(token.replace(AuthConstant.JWT_TOKEN_PREFIX, StringPoolConstant.EMPTY))
                .getBody();
        return claims;
    }

    /**
     * 获取 Authentication
     *
     * @return
     */
    private static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取登录用户信息
     *
     * @param authentication 认证信息
     * @return
     */
    public static UserDetail getUserDetail(Authentication authentication) {
        if (ValidatorUtil.isNull(authentication)) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (ValidatorUtil.isNotNull(principal) && principal instanceof UserDetail) {
            return ((UserDetail) principal);
        }
        return null;
    }

}