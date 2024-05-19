package com.master.chat.gpt.pojo.vo;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  会员用户对象 VO
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * uuid
     */
    private String uid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * openid
     */
    private String openid;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 是否开启上下文
     */
    private Boolean context;

    /**
     * 使用调用次数
     */
    private Integer useNum;

    /**
     * 剩余调用次数
     */
    private Long num;

    /**
     * 邀请人
     */
    private Long shareId;

    /**
     * 用户类型 1 微信小程序 2 公众号 3 手机号
     */
    private Integer type;

    /**
     * 状态 0 禁用 1 启用
     */
    private Integer status;

}
