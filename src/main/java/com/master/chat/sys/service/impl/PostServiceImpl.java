package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.PostMapper;
import com.master.chat.sys.mapper.SysUserPostMapper;
import com.master.chat.sys.pojo.command.PostCommand;
import com.master.chat.sys.pojo.entity.Post;
import com.master.chat.sys.pojo.entity.SysUserPost;
import com.master.chat.sys.pojo.vo.PostVO;
import com.master.chat.sys.service.IPostService;
import com.master.common.api.IPageInfo;
import com.master.common.api.Query;
import com.master.common.api.ResponseInfo;
import com.master.common.utils.DozerUtil;
import com.master.common.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public ResponseInfo<IPageInfo<PostVO>> pagePost(Query query) {
        QueryWrapper<Post> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), Post::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), Post::getStatus, query.getStatus());
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("code")), Post::getCode, query.get("code"));
        IPage<Post> iPage = postMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), qw);
        return ResponseInfo.success(IPageInfo.getIPage(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getRecords(), PostVO.class));
    }

    @Override
    public ResponseInfo<List<PostVO>> listPost(Query query) {
        QueryWrapper<Post> qw = new QueryWrapper<>();
        qw.lambda().like(ValidatorUtil.isNotNull(query.getName()), Post::getName, query.getName());
        qw.lambda().like(ValidatorUtil.isNotNull(query.getStatus()), Post::getStatus, query.getStatus());
        qw.lambda().like(ValidatorUtil.isNotNull(query.get("code")), Post::getCode, query.get("code"));
        return ResponseInfo.success(DozerUtil.convertor(postMapper.selectList(qw), PostVO.class));
    }

    @Override
    public ResponseInfo<PostVO> getPostById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(postMapper.selectById(id), PostVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo savePost(PostCommand command) {
        Post post = DozerUtil.convertor(command, Post.class);
        post.setCreateUser(command.getOperater());
        postMapper.insert(post);
        return ResponseInfo.success(post.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updatePost(PostCommand command) {
        Post post = DozerUtil.convertor(command, Post.class);
        post.setUpdateUser(command.getOperater());
        postMapper.updateById(post);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeByPostIds(List<Long> ids) {
        if (sysUserPostMapper.selectCount(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getPostId, ids)) > 0) {
            return ResponseInfo.businessFail("岗位下存在用户，无法删除");
        }
        postMapper.deleteBatchIds(ids);
        return ResponseInfo.success();
    }

}
