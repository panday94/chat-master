package com.master.chat.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.master.chat.common.constant.ResourceConstants;
import com.master.chat.sys.mapper.ResourceMapper;
import com.master.chat.sys.mapper.RoleResourceMapper;
import com.master.chat.sys.pojo.command.ResourceCommand;
import com.master.chat.sys.pojo.dto.SysUserRolesDTO;
import com.master.chat.sys.pojo.entity.Resource;
import com.master.chat.sys.pojo.vo.MetaVO;
import com.master.chat.sys.pojo.vo.ResourceVO;
import com.master.chat.sys.pojo.vo.RouterVO;
import com.master.chat.sys.service.IResourceService;
import com.master.chat.sys.service.ISysUserRoleService;
import com.master.chat.client.model.dto.QueryDTO;
import com.master.chat.common.api.ResponseInfo;
import com.master.chat.common.constant.HttpConstant;
import com.master.chat.common.constant.StringPoolConstant;
import com.master.chat.common.enums.IntEnum;
import com.master.chat.common.enums.IntegerEnum;
import com.master.chat.common.enums.StatusEnum;
import com.master.chat.common.exception.ErrorException;
import com.master.chat.common.utils.DozerUtil;
import com.master.chat.common.utils.StringUtil;
import com.master.chat.common.utils.TreeUtil;
import com.master.chat.framework.validator.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源 服务实现类
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    private static final String NO_REDIRECT = "noRedirect";

    private Resource getResource(Long id) {
        Resource resource = resourceMapper.selectById(id);
        if (ValidatorUtil.isNull(resource)) {
            throw new ErrorException("资源信息不存在，无法操作");
        }
        return resource;
    }

    private List<Resource> listResource(Boolean isMenu, Boolean isButton, Boolean isEnabled) {
        return resourceMapper.selectList(new QueryWrapper<Resource>().lambda()
                .in(isMenu, Resource::getType, Arrays.asList(IntegerEnum.ONE.getValue(), IntegerEnum.TWO.getValue()))
                .eq(isButton, Resource::getType, IntegerEnum.THREE.getValue()).orderByAsc(Resource::getSort)
                .eq(isEnabled, Resource::getStatus, StatusEnum.ENABLED.getValue()));
    }

    @Override
    public ResponseInfo<List<ResourceVO>> listResource(QueryDTO query) {
        return ResponseInfo.success(DozerUtil.convertor(resourceMapper.selectList(new QueryWrapper<Resource>().lambda()
                .like(ValidatorUtil.isNotNull(query.getName()), Resource::getName, query.getName())
                .eq(ValidatorUtil.isNotNull(query.getStatus()), Resource::getStatus, query.getStatus())
                .orderByAsc(Resource::getSort)), ResourceVO.class));
    }

    @Override
    public ResponseInfo<List<ResourceVO>> treeResource(Boolean isMenu, Boolean isButton, Boolean isEnabled) {
        return ResponseInfo.success(TreeUtil.tree(listResource(isMenu, isButton, isEnabled), ResourceVO.class));
    }

    @Override
    public ResponseInfo<List<ResourceVO>> treeResource(List<Long> roleIds, Boolean isAdmin) {
        if (isAdmin) {
            return treeResource(true, false, true);
        }
        if (ValidatorUtil.isNullIncludeArray(roleIds)) {
            return ResponseInfo.success(new ArrayList<ResourceVO>());
        }
        List<Resource> list = resourceMapper.listResource(roleIds, Arrays.asList(1, 2));
        return ResponseInfo.success(TreeUtil.tree(list, ResourceVO.class));
    }

    @Override
    public ResponseInfo<List<ResourceVO>> treeResourceByRoleId(Long roleId) {
        List<ResourceVO> list = DozerUtil.convertor(listResource(false, false, true), ResourceVO.class);
        List<Long> resourceids = roleResourceMapper.getResourceIdByRoleId(roleId);
        list = list.stream().map(v -> {
            if (resourceids.contains(v.getId())) {
                v.setChecked(true);
            }
            return v;
        }).collect(Collectors.toList());
        return ResponseInfo.success(TreeUtil.tree(list, ResourceVO.class));
    }

    @Override
    public ResponseInfo<Set<String>> listButtonResourceBySysUser(Long sysUserId, String username) {
        SysUserRolesDTO sysUserRole = sysUserRoleService.getSysUserRoles(sysUserId, username).getData();
        return listButtonResource(sysUserRole.getRoleIds(), sysUserRole.getAdmind());
    }

    @Override
    public ResponseInfo<Set<String>> listButtonResource(List<Long> roleIds, Boolean isAdmin) {
        List<Resource> list;
        if (isAdmin) {
            list = listResource(false, true, true);
            return ResponseInfo.success(list.stream().map(Resource::getPerms).filter(v -> !StringPoolConstant.EMPTY.equals(v)).collect(Collectors.toSet()));
        }
        if (ValidatorUtil.isNullIncludeArray(roleIds)) {
            return ResponseInfo.success(new HashSet<String>());
        }
        list = resourceMapper.listResource(roleIds, Arrays.asList(3));
        if (ValidatorUtil.isNullIncludeArray(list)) {
            return ResponseInfo.success(new HashSet<String>());
        }
        return ResponseInfo.success(list.stream().map(Resource::getPerms).filter(v -> !StringPoolConstant.EMPTY.equals(v)).collect(Collectors.toSet()));
    }

    @Override
    public ResponseInfo<ResourceVO> getResourceById(Long id) {
        return ResponseInfo.success(DozerUtil.convertor(resourceMapper.selectById(id), ResourceVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo saveResource(ResourceCommand command) {
        Resource resource = DozerUtil.convertor(command, Resource.class);
        resource.setCreateUser(command.getOperater());
        resourceMapper.insert(resource);
        return ResponseInfo.success(resource.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateResource(ResourceCommand command) {
        Resource resource = getResource(command.getId());
        DozerUtil.convertor(command, resource);
        resource.setUpdateUser(command.getOperater());
        resourceMapper.updateById(resource);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo updateResourceStatus(ResourceCommand command) {
        Resource resource = getResource(command.getId());
        resource.setUpdateUser(command.getOperater());
        if (ValidatorUtil.isNull(command.getStatus())) {
            resource.setStatus(StatusEnum.ENABLED.getValue().equals(resource.getStatus()) ? StatusEnum.DISABLED.getValue() : StatusEnum.ENABLED.getValue());
        } else {
            resource.setStatus(command.getStatus());
        }
        resourceMapper.updateById(resource);
        return ResponseInfo.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "masterTransactionManager")
    public ResponseInfo removeResourceById(Long id) {
        QueryWrapper<Resource> qw = new QueryWrapper<>();
        qw.lambda().eq(Resource::getParentId, id).eq(Resource::getStatus, IntegerEnum.ONE.getValue()).eq(Resource::getType, IntegerEnum.ONE.getValue());
        if (resourceMapper.selectCount(qw) > IntEnum.ZERO.getValue()) {
            return ResponseInfo.businessFail("菜单下存在子菜单，无法删除");
        }
        getResource(id);
        resourceMapper.deleteById(id);
        // 删除菜单下存在的操作资源
        qw = new QueryWrapper<>();
        qw.lambda().eq(Resource::getParentId, id).eq(Resource::getStatus, IntegerEnum.ONE.getValue()).eq(Resource::getType, IntegerEnum.TWO.getValue());
        resourceMapper.delete(qw);
        return ResponseInfo.success();
    }

    @Override
    public List<RouterVO> buildResoureces(List<ResourceVO> resourceVOS) {
        List<RouterVO> routers = new LinkedList<>();
        for (ResourceVO resource : resourceVOS) {
            RouterVO router = new RouterVO();
            router.setHidden(StatusEnum.ENABLED.getValue().equals(resource.getHidden()));
            router.setName(getRouteName(resource));
            router.setPath(getRouterPath(resource));
            router.setComponent(getComponent(resource));
            router.setQuery(resource.getQuery());
            router.setMeta(new MetaVO(resource.getName(), resource.getIcon(), resource.getPath()));
            List<ResourceVO> resourceVOList = resource.getChildren();
            if (!resourceVOList.isEmpty() && resourceVOList.size() > 0 && ResourceConstants.TYPE_DIR.equals(resource.getType())) {
                router.setAlwaysShow(true);
                router.setRedirect(NO_REDIRECT);
                router.setChildren(buildResoureces(resourceVOList));
            } else if (isMenuFrame(resource)) {
                router.setMeta(null);
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                children.setPath(resource.getPath());
                children.setComponent(resource.getComponent());
                children.setName(StringUtils.capitalize(resource.getPath()));
                children.setMeta(new MetaVO(resource.getName(), resource.getIcon(), resource.getPath()));
                children.setQuery(resource.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (resource.getParentId().intValue() == 0 && isInnerLink(resource)) {
                router.setMeta(new MetaVO(resource.getName(), resource.getIcon()));
                router.setPath(StringPoolConstant.SLASH);
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                String routerPath = innerLinkReplaceEach(resource.getPath());
                children.setPath(routerPath);
                children.setComponent(ResourceConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVO(resource.getName(), resource.getIcon(), resource.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    private String getRouteName(ResourceVO resource) {
        String routerName = StringUtils.capitalize(resource.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(resource)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    private String getRouterPath(ResourceVO resource) {
        String routerPath = resource.getPath();
        // 内链打开外网方式
        if (resource.getParentId().intValue() != 0 && isInnerLink(resource)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == resource.getParentId().intValue() && ResourceConstants.TYPE_DIR.equals(resource.getType())
                && ResourceConstants.NO_FRAME.equals(resource.getRedirect())) {
            routerPath = StringPoolConstant.SLASH + resource.getPath();
        } else if (isMenuFrame(resource)) {
            // 非外链并且是一级目录（类型为菜单）
            routerPath = StringPoolConstant.SLASH;
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    private String getComponent(ResourceVO resource) {
        String component = ResourceConstants.LAYOUT;
        if (ValidatorUtil.isNotNull(resource.getComponent()) && !isMenuFrame(resource)) {
            component = resource.getComponent();
        } else if (ValidatorUtil.isNull(resource.getComponent()) && resource.getParentId().intValue() != 0 && isInnerLink(resource)) {
            component = ResourceConstants.INNER_LINK;
        } else if (ValidatorUtil.isNull(resource.getComponent()) && isParentView(resource)) {
            component = ResourceConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    private boolean isMenuFrame(ResourceVO resource) {
        return resource.getParentId().intValue() == 0 && ResourceConstants.TYPE_MENU.equals(resource.getType())
                && ResourceConstants.NO_FRAME.equals(resource.getRedirect());
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(ResourceVO resource) {
        return ResourceConstants.NO_FRAME.equals(resource.getRedirect()) && StringUtil.ishttp(resource.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    private boolean isParentView(ResourceVO resource) {
        return resource.getParentId().intValue() != 0 && ResourceConstants.TYPE_DIR.equals(resource.getType());
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    private String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{HttpConstant.HTTP, HttpConstant.HTTPS}, new String[]{StringPoolConstant.EMPTY, StringPoolConstant.EMPTY});
    }

}
