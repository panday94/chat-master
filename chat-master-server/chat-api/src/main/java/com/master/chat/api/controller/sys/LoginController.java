package com.master.chat.api.controller.sys;

import com.master.chat.api.base.BaseController;
import com.master.chat.api.manger.AsyncManager;
import com.master.chat.api.manger.factory.AsyncFactory;
import com.master.chat.api.security.JwtTokenUtils;
import com.master.chat.api.security.Oauth2Token;
import com.master.chat.api.security.UserDetail;
import com.master.chat.common.constant.RedisConstants;
import com.master.chat.common.constant.SysConfigConstants;
import com.master.chat.framework.util.RedisUtils;
import com.master.chat.sys.pojo.command.LoginCommand;
import com.master.chat.sys.service.ILoginLogService;
import com.master.chat.sys.service.ISysConfigService;
import com.master.chat.sys.service.ISysUserService;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.framework.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

/**
 * 登录登出接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@RestController
public class LoginController extends BaseController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private ILoginLogService loginLogService;
    @Autowired
    private RedisUtils redisUtil;

    /**
     * 登录
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/oauth/token")
    public ResponseInfo<Oauth2Token> login(LoginCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword());
        Authentication auth = null;
        ResponseInfo<Oauth2Token> response = ResponseInfo.unauthorized();
        try {
            auth = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            if (ResponseEnum.ACCOUNT_NOT_EXIST.getMsg().equals(e.getMessage())) {
                response = ResponseInfo.accountNotExist();
            } else if (ResponseEnum.ACCOUNT_IS_DISABLED.getMsg().equals(e.getMessage())) {
                response = ResponseInfo.accountDisabled();
            } else if (ResponseEnum.PASSWORD_ERROR.getMsg().equals(e.getMessage())) {
                response = ResponseInfo.passwordError();
            } else if (e instanceof BadCredentialsException) {
                response = ResponseInfo.passwordError();
            } else if (e instanceof CredentialsExpiredException) {
                response = ResponseInfo.unauthorized();
            } else {
                response = ResponseInfo.error(Optional.ofNullable(e.getMessage()).orElse("登录失败"));
            }
        }
        //如果认证没通过给出相应的提示
        if (ValidatorUtil.isNull(auth)) {
            AsyncManager.me().execute(AsyncFactory.addLoginLog(null, command.getUsername(), StatusEnum.DISABLED.getValue(), StringPoolConstant.EMPTY, response.getMsg()));
            return response;
        }
        //判断验证码是否正确
        if (ValidatorUtil.isNotNull(command.getCode())) {
            configService.validateCaptcha(command.getCode(), command.getUuid());
        }
        // 存储登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(auth);
        Oauth2Token token = Oauth2Token.builder().token(JwtTokenUtils.generateToken(auth)).expiresIn(RedisConstants.EXPIRE_TIME / 1000L).build();
        // 添加登录日志
        saveLoginLog(token.getToken());
        return ResponseInfo.success(token);
    }

    /**
     * 添加登录日志
     *
     * @param token 用户token
     */
    private void saveLoginLog(String token) {
        UserDetail userDetail = JwtTokenUtils.getLoginUser();
        String allLogin = redisUtil.get(RedisConstants.SYS_CONFIG_KEY + SysConfigConstants.ALL_LOGIN);
        String key = RedisConstants.LOGIN_TOKEN_KEY + userDetail.getId() + StringPoolConstant.COLON;
        if (StringPoolConstant.FALSE.equalsIgnoreCase(allLogin)) {
            Set<String> keys = redisUtil.getKeys(key + StringPoolConstant.ASTERISK);
            redisUtil.del(keys);
            loginLogService.disableLogin(userDetail.getId());
        }
        sysUserService.updateLoginTime(userDetail.getId());
        redisUtil.set(key + userDetail.getSessionId(), token, RedisConstants.EXPIRE_TIME / 1000L);
        loginLogService.saveLoginLog(userDetail.getId(), userDetail.getSessionId(),
                userDetail.getUsername(), StatusEnum.ENABLED.getValue(), token, "登录成功");
    }

    /**
     * 登出
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/logout")
    public ResponseInfo logout() {
        String token = getToken();
        if (ValidatorUtil.isNull(token)) {
            return ResponseInfo.success();
        }
        try {
            UserDetail userDetail = JwtTokenUtils.getUserDetailFromToken(token);
            String key = RedisConstants.LOGIN_TOKEN_KEY + userDetail.getId() + StringPoolConstant.COLON;
            redisUtil.del(key + userDetail.getSessionId());
            loginLogService.disableLogin(userDetail.getId(), userDetail.getSessionId());
            return ResponseInfo.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseInfo.success();
        }
    }

}
