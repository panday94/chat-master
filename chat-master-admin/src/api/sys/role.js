import request from '@/utils/request'

// 查询角色列表
export function listRole(query) {
  return request({
    url: '/role/page',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getRole(roleId) {
  return request({
    url: '/role/' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/role',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/role',
    method: 'put',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/role/status',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: '/role/' + roleId,
    method: 'delete'
  })
}

// 获取角色资源id数组
export function roleResourceIds(roleId) {
  return request({
    url: '/role/resource/ids/' + roleId,
    method: 'get'
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/role/dataScope',
    method: 'put',
    data: data
  })
}

// 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request({
    url: '/role/user/authorization/page',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/role/user/unauthorization/page',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  return request({
    url: '/role/user/unauthorization',
    method: 'put',
    data: data
  })
}

// 授权用户选择
export function authUserSelectAll(data) {
  return request({
    url: '/role/user/authorization',
    method: 'put',
    data: data
  })
}