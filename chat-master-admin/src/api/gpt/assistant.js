import request from '@/utils/request'

// 查询分页AI助理功能列表
export function pageAssistant(query) {
  return request({
    url: '/gpt/assistant/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶AI助理功能列表
export function listAssistant(query) {
  return request({
    url: '/gpt/assistant/list',
    method: 'get',
    params: query
  })
}

// 查询AI助理功能详细
export function getAssistant(id) {
  return request({
    url: '/gpt/assistant/' + id,
    method: 'get'
  })
}

// 新增AI助理功能
export function addAssistant(data) {
  return request({
    url: '/gpt/assistant',
    method: 'post',
    data: data
  })
}

// 修改AI助理功能
export function updateAssistant(data) {
  return request({
    url: '/gpt/assistant',
    method: 'put',
    data: data
  })
}

// 删除AI助理功能
export function delAssistant(id) {
  return request({
    url: '/gpt/assistant/' + id,
    method: 'delete'
  })
}
