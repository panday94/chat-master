package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.gpt.pojo.command.AssistantCommand;
import com.master.chat.gpt.pojo.entity.Assistant;
import com.master.chat.gpt.pojo.vo.AppAssistantVO;
import com.master.chat.gpt.pojo.vo.AssistantVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * AI助理功能 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
public interface IAssistantService extends IService<Assistant> {

    /**
     * 查询AI助理功能分页列表
     *
     * @param query 查询条件
     * @return AI助理功能集合
     */
    ResponseInfo<IPageInfo<AssistantVO>> pageAssistant(Query query);

    /**
     * 查询AI助理功能列表
     *
     * @param query 查询条件
     * @return AI助理功能集合
     */
    ResponseInfo<List<AssistantVO>> listAssistant(Query query);

    /**
     * 查询AI助理功能列表
     *
     * @param query 查询条件
     * @return AI助理功能集合
     */
    ResponseInfo<List<AppAssistantVO>> listAssistantByApp(Query query);

    /**
     * 根据主键查询AI助理功能
     *
     * @param id AI助理功能主键
     * @return AI助理功能
     */
     ResponseInfo<AssistantVO> getAssistantById(Long id);

    /**
     * 新增AI助理功能
     *
     * @param command AI助理功能
     * @return 结果
     */
    ResponseInfo saveAssistant(AssistantCommand command);

    /**
     * 修改AI助理功能
     *
     * @param command AI助理功能
     * @return 结果
     */
    ResponseInfo updateAssistant(AssistantCommand command);

    /**
     * 批量删除AI助理功能
     *
     * @param ids 需要删除的AI助理功能主键集合
     * @return 结果
     */
    ResponseInfo removeAssistantByIds(List<Long> ids);

    /**
     * 删除AI助理功能信息
     *
     * @param id AI助理功能主键
     * @return 结果
     */
    ResponseInfo removeAssistantById(Long id);

}
