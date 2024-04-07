package com.master.chat.gpt.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.framework.security.JWTPasswordEncoder;
import com.master.chat.gpt.constant.BaseConfigConstant;
import com.master.chat.gpt.enums.UserTypeEnum;
import com.master.chat.gpt.mapper.UserMapper;
import com.master.chat.gpt.pojo.command.UserCommand;
import com.master.chat.gpt.pojo.entity.User;
import com.master.chat.gpt.pojo.vo.UserVO;
import com.master.chat.gpt.service.IUserService;
import com.master.chat.sys.pojo.command.SysUserPasswordCommand;
import com.master.chat.sys.pojo.dto.config.AppInfoDTO;
import com.master.chat.sys.service.IBaseConfigService;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.ResponseEnum;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.exception.ProhibitVisitException;
import com.master.chat.common.utils.CommonUtil;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.utils.RandomUtil;
import com.master.chat.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员用户 服务实现类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IBaseConfigService baseConfigService;

    /**
     * 根据id获取会员用户信息
     *
     * @param id 会员用户id
     * @return
     */
    private User getUser(Long id) {
        User user = userMapper.selectById(id);
        if (ValidatorUtil.isNull(user)) {
            throw new ErrorException("会员用户信息不存在，无法操作");
        }
        return user;
    }

    @Override
    public ResponseInfo<IPageInfo<UserVO>> pageUser(Query query) {
        IPage<UserVO> iPage = userMapper.pageUser(new Page<>(query.getCurrent(), query.getSize()), query);
        iPage.getRecords().stream().forEach(v -> v.setTel(CommonUtil.mobileEncrypt(v.getTel())));
        return ResponseInfo.success(new IPageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords()));
    }

    @Override
    public ResponseInfo<List<UserVO>> listUser(Query query) {
        List<UserVO> userVOS = userMapper.listUser(query);
        userVOS.stream().forEach(v -> v.setTel(CommonUtil.mobileEncrypt(v.getTel())));
        return ResponseInfo.success(userVOS);
    }

    @Override
    public ResponseInfo<UserVO> getUserById(Long id) {
        UserVO userVO = DozerUtil.convertor(getUser(id), UserVO.class);
        if (ValidatorUtil.isNull(userVO)) {
            throw new ProhibitVisitException("会员用户信息不存在，无法操作");
        }
        userVO.setTel(CommonUtil.mobileEncrypt(userVO.getTel()));
        return ResponseInfo.success(userVO);
    }

    @Override
    public ResponseInfo<UserVO> getLoginUserById(Long id) {
        User user = userMapper.selectById(id);
        if (ValidatorUtil.isNull(user)) {
            throw new ProhibitVisitException("会员用户信息不存在，无法操作");
        }
        UserVO userVO = DozerUtil.convertor(user, UserVO.class);
        userVO.setTel(CommonUtil.mobileEncrypt(userVO.getTel()));
        return ResponseInfo.success(userVO);
    }

    @Override
    public ResponseInfo<UserVO> getUserByUserName(String username) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getTel, username));
        return ResponseInfo.success(DozerUtil.convertor(user, UserVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo<UserVO> loginByTel(String tel, String password, String shareCode) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getTel, tel));
        if (ValidatorUtil.isNotNull(user)) {
            return ResponseInfo.success(DozerUtil.convertor(user, UserVO.class));
        }
        String name = "用户" + RandomUtil.randomString(8);
        user = User.builder()
                .loginTime(LocalDateTime.now())
                .uid(UUID.randomUUID().toString())
                .name(name).nickName(name).tel(tel).password(JWTPasswordEncoder.bcryptEncode(password))
                .type(3)
                .build();
        AppInfoDTO appInfo = baseConfigService.getBaseConfigByName(BaseConfigConstant.APP_INFO, AppInfoDTO.class);
        if (ValidatorUtil.isNotNull(appInfo) && ValidatorUtil.isNotNull(appInfo.getFreeNum())) {
            user.setNum(Long.valueOf(appInfo.getFreeNum()));
        }
        userMapper.insert(user);
        return ResponseInfo.success(DozerUtil.convertor(user, UserVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveUser(UserCommand command) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getTel, command.getTel()));
        if (ValidatorUtil.isNotNull(user)) {
            return ResponseInfo.customizeError(ResponseEnum.TEL_IS_EXIST);
        }
        user = DozerUtil.convertor(command, User.class);
        user.setCreateUser(command.getOperater());
        String name = "手机用户" + RandomUtil.randomString(6);
        user.setUid(UUID.fastUUID().toString());
        user.setName(name);
        user.setNickName(name);
        user.setType(UserTypeEnum.TEL.getValue());
        userMapper.insert(user);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateUser(UserCommand command) {
        User user = getUser(command.getId());
        user.setNickName(command.getNickName());
        user.setUpdateUser(command.getOperater());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo updateUserContext(UserCommand command) {
        User user = getUser(command.getOperaterId());
        user.setContext(command.getContext());
        userMapper.updateById(user);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo updateUserAvatar(Long id, String avatar) {
        User user = getUser(id);
        user.setAvatar(avatar);
        userMapper.updateById(user);
        return ResponseInfo.success(avatar);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo updatePassword(SysUserPasswordCommand command) {
        User user = getUser(command.getOperaterId());
        boolean isBcrypt = JWTPasswordEncoder.matchesBcrypt(user.getPassword());
        if (isBcrypt) {
            if (!JWTPasswordEncoder.matchesBcrypt(command.getOldPassword(), user.getPassword())) {
                return ResponseInfo.businessFail("旧密码错误，您还有5次机会或者联系管理员重置密码");
            }
            if (JWTPasswordEncoder.matchesBcrypt(command.getNewPassword(), user.getPassword())) {
                return ResponseInfo.businessFail("新密码与旧密码相同，无需重置");
            }
        } else {
            if (!command.getOldPassword().equals(user.getPassword())) {
                return ResponseInfo.businessFail("旧密码错误，您还有5次机会或者联系管理员重置密码");
            }
            if (command.getNewPassword().equals(user.getPassword())) {
                return ResponseInfo.businessFail("新密码与旧密码相同，无需重置");
            }
        }
        user.setPassword(JWTPasswordEncoder.bcryptEncode(command.getNewPassword()));
        userMapper.updateById(user);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeUserByIds(List<Long> ids) {
        userMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeUserById(Long id) {
        userMapper.deleteById(id);
        return ResponseInfo.success();
    }

}
