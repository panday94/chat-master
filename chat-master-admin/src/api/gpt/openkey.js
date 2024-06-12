import request from '@/utils/request'

// 查询分页openai token列表
export function pageOpenkey(query) {
  return request({
    url: '/gpt/openkey/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶openai token列表
export function listOpenkey(query) {
  return request({
    url: '/gpt/openkey/list',
    method: 'get',
    params: query
  })
}

// 查询openai token详细
export function getOpenkey(id) {
  return request({
    url: '/gpt/openkey/' + id,
    method: 'get'
  })
}

// 新增openai token
export function addOpenkey(data) {
  return request({
    url: '/gpt/openkey',
    method: 'post',
    data: data
  })
}

// 修改openai token
export function updateOpenkey(data) {
  return request({
    url: '/gpt/openkey',
    method: 'put',
    data: data
  })
}

// 删除openai token
export function delOpenkey(id) {
  return request({
    url: '/gpt/openkey/' + id,
    method: 'delete'
  })
}
