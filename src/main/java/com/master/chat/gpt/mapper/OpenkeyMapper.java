package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.Openkey;
import com.master.chat.gpt.pojo.vo.OpenkeyVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * openai token Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
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
