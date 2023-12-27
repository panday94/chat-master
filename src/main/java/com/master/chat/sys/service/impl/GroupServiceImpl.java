package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.sys.mapper.GroupMapper;
import com.master.chat.sys.service.IGroupService;
import com.master.chat.sys.pojo.entity.Group;
import org.springframework.stereotype.Service;

/**
 *  用户组 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

}
