package com.master.chat.framework.validator.group;

import jakarta.validation.GroupSequence;

/**
 * @description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author: Yang
 * @date: 2019/8/16
 * @version: 3.0.0
 * https://chatmaster.top
 * Copyright Ⓒ 2023 熊扬软件开发工作室 Limited All rights reserved.
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
