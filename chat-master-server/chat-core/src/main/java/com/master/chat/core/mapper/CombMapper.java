package com.master.chat.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.client.model.dto.Query;
import com.master.chat.core.pojo.entity.Comb;
import com.master.chat.core.pojo.vo.CombVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员套餐 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public interface CombMapper extends BaseMapper<Comb> {

    /**
     * 分页查询会员套餐列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<CombVO> pageComb(IPage page, @Param("q") Query query);

    /**
     * 查询会员套餐列表
     *
     * @param query 查询条件
     * @return
     */
    List<CombVO> listComb(@Param("q") Query query);

    /**
     * 查询会员套餐
     *
     * @param query 查询条件
     * @return
     */
    CombVO getComb(@Param("q") Query query);

}
