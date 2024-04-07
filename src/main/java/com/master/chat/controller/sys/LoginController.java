package com.master.chat.controller.sys;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.master.chat.comm.constant.Constants;
import com.master.chat.comm.constant.RedisConstants;
import com.master.chat.comm.constant.SysConfigConstants;
import com.master.chat.comm.util.RedisUtils;
import com.master.chat.framework.base.BaseController;
import com.master.chat.framework.manger.AsyncManager;
import com.master.chat.framework.manger.factory.AsyncFactory;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.framework.security.Oauth2Token;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.sys.pojo.command.LoginCommand;
import com.master.chat.sys.service.ILoginLogService;
import com.master.chat.sys.service.ISysConfigService;
import com.master.chat.sys.service.ISysUserService;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.validator.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
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
        String allLogin = redisUtil.get(RedisConstants.SYS_CONFIG_KEY + SysConfigConstants.ALL_LOGIN);
        String key = RedisConstants.LOGIN_TOKEN_KEY + userDetail.getId() + StringPoolConstant.COLON;
        if (StringPoolConstant.FALSE.equalsIgnoreCase(allLogin)) {
            Set<String> keys = redisUtil.getKeys(key + StringPoolConstant.ASTERISK);
            redisUtil.del(keys);
            loginLogService.disableLogin(userDetail.getId());
        }
        sysUserService.updateLoginTime(userDetail.getId());
        redisUtil.set(key + userDetail.getSessionId(), token, JwtTokenUtils.EXPIRE_TIME / 1000L);
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

    /**
     * 生成验证码
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/captchaImage")
    public ResponseInfo getCode(HttpServletResponse response) throws IOException {
        Map<String, Object> result = new HashMap<>(16);
        Boolean flag = configService.getKeyValue(SysConfigConstants.CAPTCHA_ON_OFF);
        result.put("captchaOnOff", flag);
        if (!flag) {
            return ResponseInfo.success(result);
        }

        // 保存验证码信息
        String uuid = UUID.randomUUID().toString(true);
        String verifyKey = RedisConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr;
        String code;
        BufferedImage image;

        // 生成验证码 验证码类型 math 数组计算 char 字符验证
        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        image = captchaProducerMath.createImage(capStr);

        // char计算方式
        //capStr = code = captchaProducer.createText();
        //image = captchaProducer.createImage(capStr);

        redisUtil.set(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return ResponseInfo.error(e.getMessage());
        }

        result.put("uuid", uuid);
        result.put("img", Base64.encode(os.toByteArray()));
        return ResponseInfo.success(result);
    }

}
