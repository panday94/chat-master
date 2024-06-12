package com.master.chat.api.security;

import cn.hutool.core.lang.UUID;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.sys.pojo.vo.SysUserVO;
import com.master.chat.common.enums.IntegerEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * 登录用户信息
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Data
@NoArgsConstructor
public class UserDetail implements UserDetails {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户标识
     */
    private String uid;

    /**
     * 当前会话标识
     */
    private String sessionId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否管理员 0 否 1 是
     */
    private Boolean admind;

    /**
     * 启用禁用
     */
    private Boolean enabled;

    /**
     * 账号角色 1 后台 2 用户
     */
    private Integer role;

    /**
     * 权限信息
     */
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 封装用户信息
     *
     * @param user   管理用户
     * @param encode 是否加密
     */
    public UserDetail(SysUserVO sysUser, Set<String> permissions) {
        this.setId(sysUser.getId());
        this.setUid(sysUser.getUid());
        this.setSessionId(UUID.randomUUID().toString());
        this.setUsername(sysUser.getUsername());
        this.setPassword(sysUser.getPassword());
        this.setRole(1);
        this.setAdmind(sysUser.getAdmind());
        this.setEnabled(IntegerEnum.ONE.getValue().equals(sysUser.getStatus()));
        this.permissions = permissions;
        // 角色集合
        this.authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        Optional.ofNullable(permissions).ifPresent(v -> v.stream().forEach(d -> this.authorities.add(new SimpleGrantedAuthority(d))));
    }

    /**
     * 封装用户信息
     *
     * @param user   管理用户
     * @param encode 是否加密
     */
    public UserDetail(UserVO user, Set<String> permissions) {
        this.setId(user.getId());
        this.setUid(user.getUid());
        this.setSessionId(UUID.randomUUID().toString());
        this.setUsername(user.getTel());
        this.setPassword(user.getPassword());
        this.setRole(2);
        this.setAdmind(true);
        this.setEnabled(true);
    }

    /**
     * 封装用户信息
     *
     * @param user   管理用户
     * @param encode 是否加密
     */

    public UserDetail(Long id, String uid, String sessionId, String username, Boolean admind, Integer role) {
        this.setId(id);
        this.setUid(uid);
        this.setSessionId(sessionId);
        this.setUsername(username);
        this.setAdmind(admind);
        this.setRole(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
