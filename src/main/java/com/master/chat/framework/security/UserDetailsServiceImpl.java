package com.master.chat.framework.security;

import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IUserService;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.sys.service.IResourceService;
import com.master.chat.sys.service.ISysUserService;
import com.master.chat.common.constant.AuthConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.validator.ValidatorUtil;
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
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private HttpServletRequest request;
    private static final String CLIENT_ID = "app";

    @Override
    public UserDetails loadUserByUsername(String username) {
        String clientId = request.getHeader(AuthConstant.CLIENTID_KEY);
        UserDetail userDetail;
        Set<String> permissions = new HashSet<>();
        if (ValidatorUtil.isNotNull(clientId) && CLIENT_ID.equals(clientId)) {
            UserVO user = Optional.ofNullable(userService.getUserByUserName(username).getData()).orElse(null);
            if (ValidatorUtil.isNull(user)) {
                throw new BadCredentialsException(ResponseEnum.ACCOUNT_NOT_EXIST.getMsg());
            }
            userDetail = new UserDetail(user, permissions);
        } else {
            SysUserVO sysUser = Optional.ofNullable(sysUserService.getSysUserByUsername(username).getData()).orElse(null);
            if (ValidatorUtil.isNull(sysUser)) {
                throw new BadCredentialsException(ResponseEnum.ACCOUNT_NOT_EXIST.getMsg());
            }
            // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
            permissions = resourceService.listButtonResourceBySysUser(sysUser.getId(), username).getData();
            userDetail = new UserDetail(sysUser, permissions);
        }

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