package com.master.chat.framework.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.master.chat.common.utils.SnowFlakeUtil;
import org.springframework.stereotype.Component;

/**
 * 自动生成主键雪花id
 *
 * @author: Yang
 * @date: 2023/2/7
 * @version: 1.0.0

 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Long nextId(Object entity) {
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        return SnowFlakeUtil.snowflakeId(15);
    }

}
