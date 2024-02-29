package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.DictCommand;
import com.master.chat.sys.pojo.entity.Dict;
import com.master.chat.sys.pojo.vo.DictVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 字典 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IDictService extends IService<Dict> {

    /**
     * 获取字典分页列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<IPageInfo<DictVO>> pageDict(Query query);

    /**
     * 字典列表
     *
     * @param type 类型
     * @return
     */
    ResponseInfo<List<DictVO>> listDict(String type);

    /**
     * 根据id获取字典详情
     *
     * @param id 字典id
     * @return
     */
    ResponseInfo<DictVO> getDictById(Long id);

    /**
     * 添加字典信息
     *
     * @param command 字典信息
     * @return
     */
    ResponseInfo saveDict(DictCommand command);

    /**
     * 更新字典信息
     *
     * @param command 字典信息
     * @return
     */
    ResponseInfo updateDict(DictCommand command);

    /**
     * 启用/禁用字典信息
     *
     * @param command 字典信息
     * @return
     */
    ResponseInfo updateDictStatus(DictCommand command);

    /**
     * 根据id删除字典信息
     *
     * @param ids 字典id数组
     * @return
     */
    ResponseInfo removeDictByIds(List<Long> ids);

    /**
     * 刷新字典缓存
     *
     * @return
     */
    ResponseInfo refreshDictCache();

}
