package com.master.chat.api.config;

import com.master.chat.api.security.JwtAuthenticationFilter;
import com.master.chat.api.security.UserDetailsServiceImpl;
import com.master.chat.common.constant.HttpConstant;
import com.master.chat.framework.util.JWTPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 安全配置类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDatailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //  禁用basic明文验证
                .httpBasic(Customizer.withDefaults())
                // 关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 不通过Session获取SecurityContext
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        //  允许所有 OPTIONS 请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 对应登录接口允许匿名访问
                        .requestMatchers(HttpConstant.SLASH).permitAll()
                        // 除上面接口全都需要鉴权访问
                        .anyRequest().authenticated()
                );
        //添加过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 因为有自定义拦截器，需在这边配置放行。
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/global/**", "/static/**", "/images/**", "/druid/**",
                "/oauth/token", "/logout", "/captchaImage", "/files/**", "/api/**", "/app/api/**", "/v1/chat/websocket/**",
                "/common/**", "/sys-user/register");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDatailService;
    }

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用自定义加密密码
        return new JWTPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDatailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
