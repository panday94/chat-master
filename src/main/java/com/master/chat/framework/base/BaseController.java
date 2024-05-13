package com.master.chat.framework.base;

import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.AuthConstant;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.exception.ProhibitVisitException;
import com.master.chat.common.utils.IPUtil;
import com.master.chat.common.validator.ValidatorUtil;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.framework.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础接口抽象类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0

 */
public abstract class BaseController extends ResponseInfo {
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取请求ip地址
     *
     * @return ip地址
     */
    public String getIp() {
        return IPUtil.getIpAddr(request);
    }

    /**
     * 获取请求头中token信息
     *
     * @return token
     */
    public String getToken() {
        String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        return ValidatorUtil.isNotNull(token) ? token.replace(AuthConstant.JWT_TOKEN_PREFIX, StringPoolConstant.EMPTY) : null;
    }

    /**
     * 获取登录用户信息
     */
    public UserDetail getLoginUser() {
        try {
            UserDetail userDetail = JwtTokenUtils.getLoginUser();
            if (ValidatorUtil.isNull(userDetail)) {
                throw new ProhibitVisitException();
            }
            return userDetail;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProhibitVisitException();
        }
    }

    /**
     * 获取登录用户id
     */
    public Long getSysUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取登录用户标识
     */
    public String getUid() {
        return getLoginUser().getUid();
    }

    /**
     * 获取登录用户名称
     */
    public String getSysUserName() {
        return getLoginUser().getUsername();
    }

}
