package com.master.chat.controller.app;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.master.chat.common.constant.RedisConstants;
import com.master.chat.common.util.RedisUtils;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.framework.security.Oauth2Token;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.framework.third.WxAppConstant;
import com.master.chat.framework.third.WxServiceHandler;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.sys.pojo.command.LoginCommand;
import com.master.chat.common.constant.SysConfigConstants;
import com.master.chat.gpt.enums.UserTypeEnum;
import com.master.chat.gpt.service.IUserService;
import com.master.common.api.ResponseInfo;
import com.master.common.constant.StringPoolConstant;
import com.master.common.enums.ResponseEnum;
import com.master.common.validator.ValidatorUtil;
import me.chanjar.weixin.common.error.WxErrorException;
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
 * @author: yang
 * @date: 2023/5/4
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
@RestController(value = "appLoginController")
@RequestMapping("/app")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private WxServiceHandler wxServiceHandler;
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
        if (UserTypeEnum.WXAPP.getValue().equals(command.getLoginType())) {
            WxMaPhoneNumberInfo wxInfo;
            try {
                wxInfo = wxServiceHandler.getMaService(WxAppConstant.APP_ID).getUserService().getNewPhoneNoInfo(command.getCode());
            } catch (WxErrorException e) {
                return ResponseInfo.error("获取小程序用户信息失败");
            }
            authenticationToken = new UsernamePasswordAuthenticationToken(wxInfo.getPurePhoneNumber(), StringPoolConstant.EMPTY);
        } else if (UserTypeEnum.WXMP.getValue().equals(command.getLoginType())) {
            WxMaPhoneNumberInfo wxInfo;
            try {
                wxInfo = wxServiceHandler.getMaService(WxAppConstant.APP_ID).getUserService().getNewPhoneNoInfo(command.getCode());
            } catch (WxErrorException e) {
                return ResponseInfo.error("获取小程序用户信息失败");
            }
            authenticationToken = new UsernamePasswordAuthenticationToken(wxInfo.getPurePhoneNumber(), StringPoolConstant.EMPTY);
        } else if (UserTypeEnum.TEL.getValue().equals(command.getLoginType())) {
            UserVO user = userService.loginByTel(command.getTel(), command.getPassword(), command.getCode()).getData();
            authenticationToken = new UsernamePasswordAuthenticationToken(user.getTel(), command.getPassword());
        } else if (UserTypeEnum.USERNAME.getValue().equals(command.getLoginType())) {
            authenticationToken = new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword());
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
        Oauth2Token token = Oauth2Token.builder().token(JwtTokenUtils.generateToken(auth)).expiresIn(JwtTokenUtils.EXPIRE_TIME / 1000L).build();
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
        redisUtils.set(key + userDetail.getSessionId(), token, JwtTokenUtils.EXPIRE_TIME / 1000L);
    }

}
