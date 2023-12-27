package com.master.chat.framework.aspect;

import cn.hutool.core.text.StrFormatter;
import com.master.chat.common.constant.Constants;
import com.master.chat.sys.service.ISysUserService;
import com.master.chat.framework.security.JwtTokenUtils;
import com.master.chat.framework.security.UserDetail;
import com.master.chat.sys.pojo.dto.SysUserPreDTO;
import com.master.common.annotation.DataScope;
import com.master.common.constant.StringPoolConstant;
import com.master.common.validator.ValidatorUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.StringJoiner;

/**
 * 数据过滤处理
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Aspect
@Component
public class DataScopeAspect {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";
    public static final String DEPT_ID = "dept_id";
    public static final String SYS_USER_ID = "sys_user_id";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) {
        Object params = point.getArgs()[0];
        if (ValidatorUtil.isNull(params)) {
            return;
        }
        clearDataScope(params);
        handleDataScope(params, controllerDataScope);
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user      用户
     * @param userAlias 别名
     */
    protected void handleDataScope(Object params, DataScope controllerDataScope) {
        UserDetail loginUser = JwtTokenUtils.getLoginUser();
        if (ValidatorUtil.isNull(loginUser)) {
            return;
        }
        // 如果是超级管理员，则不过滤数据
        if (Constants.ADMIN.equals(loginUser.getUsername()) || loginUser.getAdmind()) {
            return;
        }
        String userColumn = ValidatorUtil.isNotNull(controllerDataScope.userColumn()) ? controllerDataScope.userColumn() : SYS_USER_ID;
        String deptAlias = ValidatorUtil.isNotNull(controllerDataScope.deptAlias()) ? controllerDataScope.deptAlias() + StringPoolConstant.DOT : StringPoolConstant.EMPTY;
        String userAlias = ValidatorUtil.isNotNull(controllerDataScope.userAlias()) ? controllerDataScope.userAlias() + StringPoolConstant.DOT : StringPoolConstant.EMPTY;
        deptAlias = deptAlias + DEPT_ID;
        userAlias = userAlias + userColumn;

        StringJoiner sqlString = new StringJoiner(StringPoolConstant.EMPTY);
        SysUserPreDTO sysUserPre = sysUserService.getDeptPermissionsInfo(loginUser.getId());
        if (sysUserPre.getDeptLeader() && ValidatorUtil.isNotNullIncludeArray(sysUserPre.getDeptIds())) {
            String ids = sysUserPre.getDeptIds().toString();
            ids = ids.substring(1, ids.length() - 1).replaceAll("\\s*", "");
            sqlString.add(StrFormatter.format(" {} IN ( {} ) or {} = {}", deptAlias, ids, userAlias, sysUserPre.getId()));
        } else {
            sqlString.add(StrFormatter.format(" {} = {} ", userAlias, sysUserPre.getId()));
        }
        if (params instanceof Map) {
            Map query = (Map) params;
            query.put(DATA_SCOPE, " AND (" + sqlString.toString() + ") ");
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(Object params) {
        if (params instanceof Map) {
            Map query = (Map) params;
            query.put(DATA_SCOPE, StringPoolConstant.EMPTY);
        }
    }

}
