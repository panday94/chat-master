package com.master.chat.framework.validator.group;

import javax.validation.GroupSequence;

/**
 * @description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author: Yang
 * @date: 2019/8/16
 * @version: 3.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
