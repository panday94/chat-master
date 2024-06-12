/*
 * @Descripttion: 
 * @version: v1.0.0
 * @Author: yang
 * @Date: 2022-08-03 17:18:05
 * @LastEditors: yang
 * @LastEditTime: 2022-08-08 18:01:50
 */
import request from '@/utils/request'

// 上传文件
export function uploadFile(data) {
  return request({
    url: '/common/file/upload',
    method: 'post',
    data: data
  })
}
 
// 批量上传文件
export function uploadFiles(data) {
  return request({
    url: '/common/file/batch/upload',
    method: 'post',
    data: data
  })
}

// 岗位列表筛选
export function selectPost() {
  return request({
    url: '/common/post/select',
    method: 'get'
  })
}

// 角色列表筛选
export function selectRole() {
  return request({
    url: '/common/role/select',
    method: 'get'
  })
}

// 账号列表筛选
export function selectSysUser() {
  return request({
    url: '/common/sys-user/select',
    method: 'get'
  })
}

// 字典类型筛选列表
export function optionselect() {
  return request({
    url: '/common/dict/type/select',
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return request({
    url: '/common/dict/select/' + dictType,
    method: 'get'
  })
}

// 根据参数键名查询参数值
export function getConfigKey(configKey) {
  return request({
    url: '/common/config/configKey/' + configKey,
    method: 'get'
  })
}

// 获取助手分类
export function selectAssyistantType() {
  return request({
    url: '/common/assistant-type/select',
    method: 'get'
  })
}