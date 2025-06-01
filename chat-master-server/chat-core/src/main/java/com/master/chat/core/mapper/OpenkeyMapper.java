package com.master.chat.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.client.model.dto.Query;
import com.master.chat.core.pojo.entity.Openkey;
import com.master.chat.core.pojo.vo.OpenkeyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * openai token Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
public interface OpenkeyMapper extends BaseMapper<Openkey> {

    /**
     * 分页查询openai token列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return
     */
    IPage<OpenkeyVO> pageOpenkey(IPage page, @Param("q") Query query);

    /**
     * 查询openai token列表
     *
     * @param query 查询条件
     * @return
     */
    List<OpenkeyVO> listOpenkey(@Param("q") Query query);

    /**
     * 查询openai token列表
     *
     * @param model 模型
     * @return
     */
    List<OpenkeyVO> listOpenkeyByModel(@Param("model") String model);

    /**
     * 查询openai token
     *
     * @param query 查询条件
     * @return
     */
    OpenkeyVO getOpenkey(@Param("q") Query query);

    /**
     * 更新使用token
     *
     * @param appKey     appkey
     * @param usedTokens 使用token
     */
    void updateUsedTokens(@Param("appKey") String appKey, @Param("usedTokens") Long usedTokens);

}
