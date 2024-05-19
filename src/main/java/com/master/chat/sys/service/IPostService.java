package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.PostCommand;
import com.master.chat.sys.pojo.entity.Post;
import com.master.chat.sys.pojo.vo.PostVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 岗位 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IPostService extends IService<Post> {

    /**
     * 获取岗位分页列表
     *
     * @param query 查询参数
     * @return
     */
    ResponseInfo<IPageInfo<PostVO>> pagePost(Query query);

    /**
     * 获取岗位列表
     *
     * @param query 查询参数
     * @return
     */
    ResponseInfo<List<PostVO>> listPost(Query query);

    /**
     * 根据岗位id查询部门详情
     *
     * @param id 岗位id
     * @return
     */
    ResponseInfo<PostVO> getPostById(Long id);

    /**
     * 保存岗位
     *
     * @param command 岗位信息
     * @return
     */
    ResponseInfo savePost(PostCommand command);

    /**
     * 修改岗位
     *
     * @param command 岗位信息
     * @return
     */
    ResponseInfo updatePost(PostCommand command);

    /**
     * 根据id删除岗位
     *
     * @param ids 岗位id数组
     * @return
     */
    ResponseInfo removeByPostIds(List<Long> ids);

}
