import request from '@/utils/request'

// 查询分页基础配置列表
export function pageBaseConfig(query) {
  return request({
    url: '/sys/base-config/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶基础配置列表
export function listBaseConfig(query) {
  return request({
    url: '/sys/base-config/list',
    method: 'get',
    params: query
  })
}

// 查询基础配置详细
export function getBaseConfig(name) {
  return request({
    url: '/sys/base-config/' + name,
    method: 'get'
  })
}

// 新增基础配置
export function addBaseConfig(data) {
  return request({
    url: '/sys/base-config',
    method: 'post',
    data: data
  })
}

// 修改基础配置
export function updateBaseConfig(data) {
  return request({
    url: '/sys/base-config',
    method: 'put',
    data: data
  })
}

// 删除基础配置
export function delBaseConfig(id) {
  return request({
    url: '/sys/base-config/' + id,
    method: 'delete'
  })
}
