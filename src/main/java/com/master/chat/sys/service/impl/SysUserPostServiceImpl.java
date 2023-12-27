package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.SysUserPostMapper;
import com.master.chat.sys.service.ISysUserPostService;
import com.master.chat.sys.pojo.entity.SysUserPost;
import com.master.common.api.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户岗位关联 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {
    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public ResponseInfo<List<Long>> getSysUserPostIds(Long sysUserId) {
        return ResponseInfo.success(sysUserPostMapper.getSysUserPostIds(sysUserId));
    }

    private Integer saveSysUserPost(Long sysUserId, Long postId, String operater) {
        SysUserPost sysUserPost = SysUserPost.builder()
                .createUser(operater).sysUserId(sysUserId).postId(postId).build();
        return sysUserPostMapper.insert(sysUserPost);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveSysUserPost(Long sysUserId, List<Long> postIds, String operater) {
        sysUserPostMapper.clearSysUserPost(sysUserId);
        postIds.stream().forEach(v -> {
            saveSysUserPost(sysUserId, v, operater);
        });
        return ResponseInfo.success();
    }

}
