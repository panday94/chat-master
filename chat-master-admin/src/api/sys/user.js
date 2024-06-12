import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/common";

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/sys-user/page',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/sys-user/' + parseStrEmpty(userId),
    method: 'get'
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/sys-user',
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/sys-user',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/sys-user',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/sys-user/' + userId,
    method: 'delete'
  })
}

// 用户状态修改
export function changeUserStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/sys-user/status',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function resetUserPwd(id, password) {
  const data = {
    id,
    password 
  }
  return request({
    url: '/sys-user/password/reset',
    method: 'put',
    data: data
  })
}

// 用户密码修改
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/sys-user/password/update',
    method: 'put',
    data: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/sys-user/avatar',
    method: 'post',
    data: data
  })
}
