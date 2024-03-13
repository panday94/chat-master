package com.master.chat.gpt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.master.chat.gpt.pojo.command.UploadFileCommand;
import com.master.chat.gpt.pojo.entity.UploadFile;
import com.master.chat.gpt.pojo.vo.UploadFileVO;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.common.api.Query;
import com.master.chat.common.api.ResponseInfo;

import java.util.List;

/**
 * 文件管理 服务类
 *
 * @author: Yang
 * @date: 2023-04-28
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public interface IUploadFileService extends IService<UploadFile> {

    /**
     * 查询文件管理分页列表
     *
     * @param query 查询条件
     * @return 文件管理集合
     */
    ResponseInfo<IPageInfo<UploadFileVO>> pageFile(Query query);

    /**
     * 查询文件管理列表
     *
     * @param query 查询条件
     * @return 文件管理集合
     */
    ResponseInfo<List<UploadFileVO>> listFile(Query query);

    /**
     * 根据主键查询文件管理
     *
     * @param id 文件管理主键
     * @return 文件管理
     */
     ResponseInfo<UploadFileVO> getFileById(Long id);

    /**
     * 新增文件管理
     *
     * @param command 文件管理
     * @return 结果
     */
    ResponseInfo saveFile(UploadFileCommand command);

    /**
     * 修改文件管理
     *
     * @param command 文件管理
     * @return 结果
     */
    ResponseInfo updateFile(UploadFileCommand command);

    /**
     * 批量删除文件管理
     *
     * @param ids 需要删除的文件管理主键集合
     * @return 结果
     */
    ResponseInfo removeFileByIds(List<Long> ids);

    /**
     * 删除文件管理信息
     *
     * @param id 文件管理主键
     * @return 结果
     */
    ResponseInfo removeFileById(Long id);

}
