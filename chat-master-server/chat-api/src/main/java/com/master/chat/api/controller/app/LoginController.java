package com.master.chat.api.controller.app;

import com.master.chat.api.security.JwtTokenUtils;
import com.master.chat.api.security.Oauth2Token;
import com.master.chat.api.security.UserDetail;
import com.master.chat.common.constant.RedisConstants;
import com.master.chat.common.constant.SysConfigConstants;
import com.master.chat.framework.util.RedisUtils;
import com.master.chat.gpt.enums.UserTypeEnum;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IUserService;
import com.master.chat.sys.pojo.command.LoginCommand;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.AuthConstant;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.framework.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

/**
 * 登陆登出接口
 *
 * @author: Yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController(value = "appLoginController")
@RequestMapping("/app")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登录
     *
     * @author: Yang
     * @date: 2023/1/9
     * @version: 1.0.0
     */
    @GetMapping("/api/oauth/token")
    public ResponseInfo<Oauth2Token> login(LoginCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken;
        if (UserTypeEnum.TEL.getValue().equals(command.getLoginType())) {
            String smsCode = redisUtils.get(RedisConstants.TEL_CODE + command.getTel());
            if (ValidatorUtil.isNull(smsCode) || !smsCode.equals(command.getCode())) {
                return ResponseInfo.customizeError(ResponseEnum.SMS_ERROR);
            }
            UserVO user = userService.loginByTel(command.getTel(), command.getPassword(), command.getShareCode()).getData();
            authenticationToken = new UsernamePasswordAuthenticationToken(UserTypeEnum.TEL.getPrefix() + user.getTel(), AuthConstant.NOOP + user.getPassword());
        } else if (UserTypeEnum.USERNAME.getValue().equals(command.getLoginType())) {
            UserVO user = userService.loginByTel(command.getTel(), command.getPassword(), command.getShareCode()).getData();
            authenticationToken = new UsernamePasswordAuthenticationToken(UserTypeEnum.USERNAME.getPrefix() + user.getTel(), command.getPassword());
        } else {
            return ResponseInfo.validateFail("未知的登录方式，请确认登录方式");
        }
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
            return response;
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
        String allLogin = redisUtils.get(RedisConstants.SYS_CONFIG_KEY + SysConfigConstants.ALL_LOGIN);
        String key = RedisConstants.LOGIN_TOKEN_KEY + userDetail.getId() + StringPoolConstant.COLON;
        if (StringPoolConstant.FALSE.equalsIgnoreCase(allLogin)) {
            Set<String> keys = redisUtils.getKeys(key + StringPoolConstant.ASTERISK);
            redisUtils.del(keys);
        }
        redisUtils.set(key + userDetail.getSessionId(), token, RedisConstants.EXPIRE_TIME / 1000L);
    }

}
