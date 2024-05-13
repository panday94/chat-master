package com.master.chat.gpt.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.master.chat.framework.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

;

/**
 * 聊天摘要对象 gpt_chat
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0

 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("gpt_chat")
public class Chat extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 聊天编号
     */
    private String chatNumber;

    /**
     * 角色id
     */
    private Long assistantId;

    /**
     * 会员id
     */
    private Long userId;

    /**
     * 聊天摘要
     */
    private String title;

    /**
     * 是否删除 0->未删除;1->已删除
     */
    @TableLogic
    private Integer deleted;

}
