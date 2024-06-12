package com.master.chat.api.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.master.chat.gpt.pojo.vo.AssistantTypeVO;
import com.master.chat.sys.pojo.vo.*;
import com.master.chat.common.api.SelectDTO;
import com.master.chat.common.converter.ToNumberConverter;
import com.master.chat.common.utils.DozerUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.dozermapper.core.loader.api.FieldsMappingOptions.customConverter;

/**
 * dozer 实体映射配置
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Configuration
public class DozerConfig {

    @Bean
    public Mapper dozerMapper() {
        Mapper mapper = DozerBeanMapperBuilder.create()
                //指定 dozer mapping 的配置文件(放到 resources 类路径下即可)，可添加多个 xml 文件，用逗号隔开
                .withMappingFiles("dozerBeanMapping.xml")
                .withMappingBuilder(beanMappingBuilder())
                .build();
        DozerUtil.init(mapper);
        return mapper;
    }

    @Bean
    public BeanMappingBuilder beanMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                // 个性化配置添加在此
                mapping(PostVO.class, SelectDTO.class).fields("name", "label").fields("id", "value", customConverter(ToNumberConverter.class));
                mapping(RoleVO.class, SelectDTO.class).fields("name", "label").fields("id", "value", customConverter(ToNumberConverter.class));
                mapping(SysUserVO.class, SelectDTO.class).fields("name", "label").fields("id", "value", customConverter(ToNumberConverter.class));
                mapping(ResourceVO.class, SelectDTO.class).fields("name", "label");
                mapping(DictTypeVO.class, SelectDTO.class).fields("name", "label").fields("type", "value");
                mapping(AssistantTypeVO.class, SelectDTO.class).fields("name", "label").fields("id", "value");
                //为不同名的 property 手动配置映射关系
//                mapping(DozerDO.class, DozerDTO.class).fields("code", "cityCode");
                //关闭隐式匹配(即只转换配置的属性code→cityCode)
//                mapping(DozerDO.class, DozerDTO.class, TypeMappingOptions.wildcard(false)).fields("code", "cityCode");
                //转换日期类型
//                mapping(DozerDO.class, DozerDTO.class, TypeMappingOptions.dateFormat("yyyy-MM-dd")).fields("code", "cityCode");
                //排除money字段
//                mapping(DozerDO.class, DozerDTO.class).exclude("money").fields("code", "cityCode");
                //深度索引匹配
//                mapping(DozerDO.class, DozerDTO.class).fields("childs[0].techername", "cityCode");
                //设置 mapId， 在转换的时候指定 mapId，mapId 可以设置在类级别，也可以设置在 field 级别，实现一次定义，多处使用，同时也可以设置转换方向从默认的双向变为单向(one way)
//                mapping(DozerDO.class, DozerDTO.class, TypeMappingOptions.mapId("userFieldOneWay")).fields("code", "cityCode", FieldsMappingOptions.useMapId("addrAllProperties"), FieldsMappingOptions.oneWay());
                //自定义Converter类型转换
//                mapping(DozerDO.class, DozerDTO.class).fields("status", "status", customConverter(DozerDoConverter.class));
            }
        };
    }

}
