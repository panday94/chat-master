package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.Agreement;
import com.master.chat.gpt.pojo.vo.AgreementVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内容管理 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
public interface AgreementMapper extends BaseMapper<Agreement> {

    /**
    * 分页查询内容管理列表
    *
    * @param page  分页参数
    * @param query 查询条件
    * @return
    */
    IPage<AgreementVO> pageContent(IPage page, @Param("q") Query query);

    /**
     * 查询内容管理列表
     *
     * @param query 查询条件
     * @return
     */
    List<AgreementVO> listContent(@Param("q") Query query);

    /**
     * 查询内容管理
     *
     * @param query 查询条件
     * @return
     */
    AgreementVO getContent(@Param("q") Query query);

}
