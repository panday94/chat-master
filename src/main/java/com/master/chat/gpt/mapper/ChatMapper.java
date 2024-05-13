package com.master.chat.gpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.master.chat.gpt.pojo.entity.Chat;
import com.master.chat.gpt.pojo.vo.ChatVO;
import com.master.chat.common.api.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天摘要 Mapper 接口
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
public interface ChatMapper extends BaseMapper<Chat> {

    /**
    * 分页查询聊天摘要列表
    *
    * @param page  分页参数
    * @param query 查询条件
    * @return
    */
    IPage<ChatVO> pageChat(IPage page, @Param("q") Query query);

    /**
     * 查询聊天摘要列表
     *
     * @param query 查询条件
     * @return
     */
    List<ChatVO> listChat(@Param("q") Query query);

    /**
     * 查询聊天摘要
     *
     * @param query 查询条件
     * @return
     */
    ChatVO getChat(@Param("q") Query query);

}
