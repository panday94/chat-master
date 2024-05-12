package com.master.chat.framework.security;

import com.master.chat.common.constant.AuthConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 用户登录认证信息查询
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String CLIENT_ID = "app";
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) {
        String clientId = request.getHeader(AuthConstant.CLIENTID_KEY);
        UserDetail userDetail;
        Set<String> permissions = new HashSet<>();
        UserVO user = Optional.ofNullable(userService.getUserByUserName(username).getData()).orElse(null);
        if (ValidatorUtil.isNull(user)) {
            throw new BadCredentialsException(ResponseEnum.ACCOUNT_NOT_EXIST.getMsg());
        }
        userDetail = new UserDetail(user, permissions);
        if (!userDetail.isEnabled()) {
            throw new DisabledException(ResponseEnum.ACCOUNT_IS_DISABLED.getMsg());
        } else if (!userDetail.isAccountNonLocked()) {
            throw new LockedException(ResponseEnum.ACCOUNT_IS_DISABLED.getMsg());
        } else if (!userDetail.isAccountNonExpired()) {
            throw new AccountExpiredException(ResponseEnum.ACCOUNT_IS_DISABLED.getMsg());
        }
        return userDetail;
    }

}