package com.master.chat.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.comm.constant.RedisConstants;
import com.master.chat.comm.util.RedisUtils;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.sys.mapper.LoginLogMapper;
import com.master.chat.sys.pojo.entity.LoginLog;
import com.master.chat.sys.pojo.vo.LoginLogVO;
import com.master.chat.sys.service.ILoginLogService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.utils.*;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录日志 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private RedisUtils redisUtil;

    /**
     * 获取筛选条件
     *
     * @param query
     * @return
     */
    private QueryWrapper<LoginLog> getQw(Query query) {
        QueryWrapper<LoginLog> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("address")), LoginLog::getAddress, query.get("address"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("username")), LoginLog::getUsername, query.get("username"));
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), LoginLog::getStatus, query.getStatus());
        qw.lambda().ge(ValidatorUtil.isNotNull(query.getStartDate()), LoginLog::getCreateTime, query.getStartDate());
        qw.lambda().le(ValidatorUtil.isNotNull(query.getEndDate()), LoginLog::getCreateTime, query.getEndDate());
        // 获取在线用户信息
        if (ValidatorUtil.isNotNull(query.get("online"))) {
            qw.lambda().eq(LoginLog::getStatus, StatusEnum.ENABLED.getValue());
            qw.lambda().eq(LoginLog::getEnabled, StatusEnum.ENABLED.getValue());
            qw.lambda().ge(LoginLog::getExpireTime, DateUtil.getCurrentDateTime());

        }
        qw.lambda().orderByDesc(LoginLog::getId);
        return qw;
    }

    @Override
    public ResponseInfo<IPageInfo<LoginLogVO>> pageLoginLog(Query query) {
        IPage<LoginLog> iPage = loginLogMapper.selectPage(new Page<LoginLog>(query.getCurrent(), query.getSize()), getQw(query));
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), LoginLogVO.class));
    }

    @Override
    public ResponseInfo<List<LoginLogVO>> listLoginLog(Query query) {
        return ResponseInfo.success(DozerUtil.convertor(loginLogMapper.selectList(getQw(query)), LoginLogVO.class));
    }

    @Override
    public ResponseInfo saveLoginLog(LoginLog log) {
        loginLogMapper.insert(log);
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo saveLoginLog(Long sysUserId, String sessionId, String username, Integer status, String authorization, String msg) {
        HttpServletRequest request = ApplicationContextUtil.getRequest();
        String userAgentStr = request.getHeader(Header.USER_AGENT.getValue());
        final UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        final String ip = IPUtil.getIpAddr(request);
        String urlStr = request.getRequestURL().toString();
        String domain = StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath());
        LoginLog log = LoginLog.builder()
                .expireTime(LocalDateTime.now().plusSeconds(JwtTokenUtils.EXPIRE_TIME / 1000L))
                .sysUserId(sysUserId)
                .sessionId(sessionId)
                .username(username)
                .ip(ip)
                .address(AddressUtil.getRealAddressByIP(ip))
                .domain(domain)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOs().getName())
                .status(status)
                .authorization(authorization)
                .userAgent(userAgentStr)
                .msg(msg)
                .build();
        return saveLoginLog(log);
    }

    @Override
    public ResponseInfo removeLoginLogByIds(List<Long> ids) {
        loginLogMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo cleanLoginLog() {
        loginLogMapper.cleanLoginLog();
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo forceLogout(List<Long> ids) {
        List<LoginLog> loginLogs = loginLogMapper.selectBatchIds(ids);
        loginLogs.stream().forEach(v -> {
            String key = RedisConstants.LOGIN_TOKEN_KEY + v.getSysUserId() + StringPoolConstant.COLON + v.getSessionId();
            redisUtil.del(key);
            loginLogMapper.disableLoginById(v.getId());
        });
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo disableLogin(Long sysUserId) {
        loginLogMapper.disableLogin(sysUserId);
        return ResponseInfo.success();
    }

    @Override
    public ResponseInfo disableLogin(Long sysUserId, String sessionId) {
        loginLogMapper.disableLoginBySession(sysUserId, sessionId);
        return ResponseInfo.success();
    }

}
