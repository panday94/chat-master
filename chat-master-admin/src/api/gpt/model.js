import request from '@/utils/request'

// 查询分页大模型信息列表
export function pageModel(query) {
  return request({
    url: '/gpt/model/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶大模型信息列表
export function listModel(query) {
  return request({
    url: '/gpt/model/list',
    method: 'get',
    params: query
  })
}

// 查询大模型信息详细
export function getModel(id) {
  return request({
    url: '/gpt/model/' + id,
    method: 'get'
  })
}

// 新增大模型信息
export function addModel(data) {
  return request({
    url: '/gpt/model',
    method: 'post',
    data: data
  })
}

// 修改大模型信息
export function updateModel(data) {
  return request({
    url: '/gpt/model',
    method: 'put',
    data: data
  })
}

// 删除大模型信息
export function delModel(id) {
  return request({
    url: '/gpt/model/' + id,
    method: 'delete'
  })
}
