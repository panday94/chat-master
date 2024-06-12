package com.master.chat.api.controller.sys;

import com.master.chat.api.base.BaseController;
import com.master.chat.framework.util.ExcelUtil;
import com.master.chat.sys.constant.SysLogTypeConstant;
import com.master.chat.sys.pojo.command.PostCommand;
import com.master.chat.sys.pojo.dto.PostExcelDTO;
import com.master.chat.sys.pojo.vo.PostVO;
import com.master.chat.sys.service.IPostService;
import com.master.chat.common.annotation.Log;
import com.master.chat.common.api.IPageInfo;
import com.master.chat.client.model.dto.Query;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.enums.BusinessTypeEnum;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.framework.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 职位接口
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@RestController
@RequestMapping("/post")
public class PostController extends BaseController {
    @Autowired
    private IPostService postService;

    /**
     * 获取岗位分页列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('sys:post:list')")
    public ResponseInfo<IPageInfo<PostVO>> pagePost(@RequestParam Map map) {
        return postService.pagePost(new Query(map, true));
    }

    /**
     * 获取岗位列表
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:post:list')")
    public ResponseInfo<List<PostVO>> listPost(@RequestParam Map map) {
        return postService.listPost(new Query(map));
    }

    /**
     * 根据id获取详情
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:post:query')")
    public ResponseInfo<PostVO> getPost(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    /**
     * 添加岗位信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('sys:post:save')")
    @Log(type = SysLogTypeConstant.POST, businessType = BusinessTypeEnum.INSERT)
    public ResponseInfo savePost(@Validated @RequestBody PostCommand command) {
        return postService.savePost(command);
    }

    /**
     * 修改岗位信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('sys:post:update')")
    @Log(type = SysLogTypeConstant.POST, businessType = BusinessTypeEnum.UPDATE)
    public ResponseInfo updatePost(@Validated(UpdateGroup.class) @RequestBody PostCommand command) {
        return postService.updatePost(command);
    }

    /**
     * 根据id删除岗位信息
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:post:remove')")
    @Log(type = SysLogTypeConstant.POST, businessType = BusinessTypeEnum.DELETE)
    public ResponseInfo removeById(@PathVariable List<Long> ids) {
        return postService.removeByPostIds(ids);
    }

    /**
     * 岗位导出
     *
     * @author: Yang
     * @date: 2023/01/31
     * @version: 1.0.0
     */
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:post:export')")
    @Log(value = "系统账号导出", type = SysLogTypeConstant.ROLE, businessType = BusinessTypeEnum.EXPORT)
    public void export(HttpServletResponse response, @RequestParam Map map) throws IOException {
        List<PostVO> postVOS = postService.listPost(new Query(map)).getData();
        ExcelUtil.write(response, "岗位列表", PostExcelDTO.class, DozerUtil.convertor(postVOS, PostExcelDTO.class));
    }

}
