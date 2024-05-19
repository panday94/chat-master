package com.master.chat.common.validator.group;

import com.master.chat.common.validator.group.AddGroup;
import com.master.chat.common.validator.group.UpdateGroup;

import javax.validation.GroupSequence;

/**
 * @description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author: Yang
 * @date: 2019/8/16
 * @version: 3.0.0

 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
