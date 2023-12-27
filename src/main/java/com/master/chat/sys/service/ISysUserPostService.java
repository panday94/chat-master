package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.entity.SysUserPost;
import com.master.common.api.ResponseInfo;

import java.util.List;

/**
 * 用户岗位关联 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface ISysUserPostService extends IService<SysUserPost> {

    /**
     * 根据用户id获取岗位id信息
     *
     * @param sysUserId 用户id
     * @return
     */
    ResponseInfo<List<Long>> getSysUserPostIds(Long sysUserId);

    /**
     * 添加用户岗位（一对多）
     *
     * @param sysUserId 用户id
     * @param postIds   多个岗位id数组
     * @param operater  操作人
     * @return
     */
    ResponseInfo saveSysUserPost(Long sysUserId, List<Long> postIds, String operater);

}
