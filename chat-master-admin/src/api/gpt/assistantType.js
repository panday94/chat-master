import request from '@/utils/request'

// 查询分页助手分类列表
export function pageAssistantType(query) {
  return request({
    url: '/gpt/assistant-type/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶助手分类列表
export function listAssistantType(query) {
  return request({
    url: '/gpt/assistant-type/list',
    method: 'get',
    params: query
  })
}

// 查询助手分类详细
export function getAssistantType(id) {
  return request({
    url: '/gpt/assistant-type/' + id,
    method: 'get'
  })
}

// 新增助手分类
export function addAssistantType(data) {
  return request({
    url: '/gpt/assistant-type',
    method: 'post',
    data: data
  })
}

// 修改助手分类
export function updateAssistantType(data) {
  return request({
    url: '/gpt/assistant-type',
    method: 'put',
    data: data
  })
}

// 删除助手分类
export function delAssistantType(id) {
  return request({
    url: '/gpt/assistant-type/' + id,
    method: 'delete'
  })
}
