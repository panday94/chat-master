package com.master.chat.gpt.service;

import com.master.chat.gpt.pojo.command.GptCommand;
import com.master.chat.gpt.pojo.dto.ChatMessageDTO;
import com.master.chat.gpt.pojo.vo.ChatMessageVO;
import com.master.chat.gpt.pojo.vo.ModelVO;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * chatgpt接口
 *
 * @author: yang
 * @date: 2023/5/5
 * @version: Copyright Ⓒ 2022 Master Computer Corporation Limited All rights reserved.
 */
public interface IGptService {

    /**
     * 根据模型名称获取模型信息
     *
     * @param model
     * @return
     */
    ResponseInfo<ModelVO> getModel(String model);


    /**
     * 提问
     *
     * @param command 提问内容
     * @return 返回json
     */
    ResponseInfo<ChatMessageVO> chat(GptCommand command);

    /**
     * 根据对话id 获取对话内容
     *
     * @param conversationId
     * @return
     */
    ChatMessageDTO getMessageByConverstationId(String conversationId);

    /**
     * 根据对话id 获取上下文内容 最多10条
     *
     * @param context        是否开启上下文
     * @param conversationId 对话id
     * @return
     */
    List<ChatMessageDTO> listMessageByConverstationId(Long userId, String conversationId);

    /**
     * 校验账户余额
     *
     * @param userId
     */
    void validateUser(Long userId);

    /**
     * 修改对话状态
     *
     * @param messageId 消息id
     * @param status    状态
     * @return 结果
     */
    void updateMessageStatus(String messageId, Integer status);

    /**
     * 更新对话使用token数
     *
     * @param messageId  消息id
     * @param usedTokens 使用token数
     * @return
     */
    void updateMessageUsedTokens(String messageId, Long usedTokens);

}
