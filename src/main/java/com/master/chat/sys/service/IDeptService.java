package com.master.chat.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.sys.pojo.command.DeptCommand;
import com.master.chat.sys.pojo.entity.Dept;
import com.master.chat.sys.pojo.vo.DeptVO;
import com.master.common.api.QueryDTO;
import com.master.common.api.ResponseInfo;

import java.util.List;

/**
 * 部门职位 服务类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 部门树列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<DeptVO>> treeDept(QueryDTO query);

    /**
     * 部门列表
     *
     * @param query 查询条件
     * @return
     */
    ResponseInfo<List<DeptVO>> listDept(QueryDTO query);


    /**
     * 根据部门id查询部门详情
     *
     * @param id 部门id
     * @return
     */
    ResponseInfo<DeptVO> getDeptById(Long id);

    /**
     * 保存部门
     *
     * @param command 部门信息
     * @return
     */
    ResponseInfo saveDept(DeptCommand command);

    /**
     * 修改部门
     *
     * @param command 部门信息
     * @return
     */
    ResponseInfo updateDept(DeptCommand command);

    /**
     * 启用/禁用部门信息
     *
     * @param command 部门信息
     * @return
     */
    ResponseInfo updateDeptStatus(DeptCommand command);

    /**
     * 根据id删除部门
     *
     * @param id
     * @return
     */
    ResponseInfo removeById(Long id);

}
