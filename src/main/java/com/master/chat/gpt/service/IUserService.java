package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.gpt.pojo.command.SysUserPasswordCommand;
import com.master.chat.gpt.pojo.command.UserCommand;
import com.master.chat.gpt.pojo.entity.User;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 会员用户 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
public interface IUserService extends IService<User> {

    /**
     * 查询会员用户分页列表
     *
     * @param query 查询条件
     * @return 会员用户集合
     */
    ResponseInfo<IPageInfo<UserVO>> pageUser(Query query);

    /**
     * 查询会员用户列表
     *
     * @param query 查询条件
     * @return 会员用户集合
     */
    ResponseInfo<List<UserVO>> listUser(Query query);

    /**
     * 根据主键查询会员用户
     *
     * @param id 会员用户主键
     * @return 会员用户
     */
    ResponseInfo<UserVO> getUserById(Long id);

    /**
     * 根据主键查询会员用户
     *
     * @param id 会员用户主键
     * @return 会员用户
     */
    ResponseInfo<UserVO> getLoginUserById(Long id);

    /**
     * 根据帐号查询会员用户
     *
     * @param id 会员用户主键
     * @return 会员用户
     */
    ResponseInfo<UserVO> getUserByUserName(String username);

    /**
     * 手机号登陆 如果没有账户就注册
     *
     * @param tel      手机号
     * @param shareCode 邀请码
     * @return
     */
    ResponseInfo<UserVO> loginByTel(String tel, String password, String shareCode);

    /**
     * 新增会员用户
     *
     * @param command 会员用户
     * @return 结果
     */
    ResponseInfo saveUser(UserCommand command);

    /**
     * 修改会员用户
     *
     * @param command 会员用户
     * @return 结果
     */
    ResponseInfo updateUser(UserCommand command);

    /**
     * 修改会员用户
     *
     * @param command 会员用户
     * @return 结果
     */
    ResponseInfo updateUserContext(UserCommand command);

    /**
     * 修改头像
     *
     * @param id     用户id
     * @param avatar 头像
     * @return
     */
    ResponseInfo updateUserAvatar(Long id, String avatar);

    /**
     * 修改账号密码
     *
     * @param command 账号信息
     * @return
     */
    ResponseInfo updatePassword(SysUserPasswordCommand command);

    /**
     * 批量删除会员用户
     *
     * @param ids 需要删除的会员用户主键集合
     * @return 结果
     */
    ResponseInfo removeUserByIds(List<Long> ids);

    /**
     * 删除会员用户信息
     *
     * @param id 会员用户主键
     * @return 结果
     */
    ResponseInfo removeUserById(Long id);

}
