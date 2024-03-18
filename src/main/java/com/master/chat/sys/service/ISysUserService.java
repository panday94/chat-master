package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.sys.pojo.command.SysUserCommand;
import com.master.chat.sys.pojo.command.SysUserPasswordCommand;
import com.master.chat.sys.pojo.dto.SysUserPreDTO;
import com.master.chat.sys.pojo.entity.SysUser;
import com.master.chat.sys.pojo.vo.ContactUserVO;
import com.master.chat.sys.pojo.vo.SysUserVO;

import java.util.List;

/**
 * 账号 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 获取账号分页列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<SysUserVO>> pageSysUser(Query query);

    /**
     * 获取账号列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<SysUserVO>> listSysUser(Query query);

    /**
     * 获取好友列表
     *
     * @param query
     * @return
     */
    ResponseInfo<List<ContactUserVO>> listContactSysUser(Query query);

    /**
     * 获取账号详情
     *
     * @param id 账号id
     * @return
     */
    ResponseInfo<SysUserVO> getSysUserById(Long id);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return
     */
    ResponseInfo<SysUserVO> getSysUserByUsername(String username);


    /**
     * 添加账号
     *
     * @param command 账号信息
     * @return
     */
    ResponseInfo saveSysUser(SysUserCommand command);

    /**
     * 修改账号
     *
     * @param command 账号信息
     * @return
     */
    ResponseInfo updateSysUser(SysUserCommand command);

    /**
     * 修改头像
     *
     * @param id     用户id
     * @param avatar 头像
     * @return
     */
    ResponseInfo updateSysUserAvatar(Long id, String avatar);

    /**
     * 启用/禁用账号信息
     *
     * @param command 账号信息
     * @return
     */
    ResponseInfo updateSysUserStatus(SysUserCommand command);

    /**
     * 重置账号密码
     *
     * @param command 账号信息
     * @return
     */
    ResponseInfo resetPassword(SysUserPasswordCommand command);

    /**
     * 修改账号密码
     *
     * @param command 账号信息
     * @return
     */
    ResponseInfo updatePassword(SysUserPasswordCommand command);

    /**
     * 修改登录时间
     *
     * @param id 账号id
     * @return
     */
    ResponseInfo updateLoginTime(Long id);

    /**
     * 将账号设置为管理员账号
     *
     * @param id 账号id
     * @return
     */
    ResponseInfo sysUserAdmind(Long id);

    /**
     * 删除账号
     *
     * @param ids 账号id数组
     * @return
     */
    ResponseInfo removeBySysUserIds(List<Long> ids);

    /**
     * 根据账号id获取部门权限信息
     *
     * @param id 账号id
     * @return
     */
    SysUserPreDTO getDeptPermissionsInfo(Long id);

}
