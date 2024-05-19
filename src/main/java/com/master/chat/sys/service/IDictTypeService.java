package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.DictTypeCommand;
import com.master.chat.sys.pojo.entity.DictType;
import com.master.chat.sys.pojo.vo.DictTypeVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 字典类型 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IDictTypeService extends IService<DictType> {

    /**
     * 获取字典类型分页列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<DictTypeVO>> pageDictType(Query query);

    /**
     * 获取字典类型列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<DictTypeVO>> listDictType(Query query);

    /**
     * 根据id获取字典类型详情
     *
     * @param id 字典类型id
     * @return
     */
    ResponseInfo<DictTypeVO> getDictTypeById(Long id);

    /**
     * 添加字典类型信息
     *
     * @param command 字典信息
     * @return
     */
    ResponseInfo saveDictType(DictTypeCommand command);

    /**
     * 更新字典类型信息
     *
     * @param command 字典类型信息
     * @return
     */
    ResponseInfo updateDictType(DictTypeCommand command);

    /**
     * 启用/禁用字典类型信息
     *
     * @param command 字典类型信息
     * @return
     */
    ResponseInfo updateDictTypeStatus(DictTypeCommand command);

    /**
     * 根据id删除字典类型信息
     *
     * @param ids 字典类型数组
     * @return
     */
    ResponseInfo removeDictTypeByIds(List<Long> ids);

}
