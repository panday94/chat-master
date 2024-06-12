import request from '@/utils/request'

// 查询分页对话消息列表
export function pageChatMessage(query) {
  return request({
    url: '/gpt/chat-message/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶对话消息列表
export function listChatMessage(query) {
  return request({
    url: '/gpt/chat-message/list',
    method: 'get',
    params: query
  })
}

// 查询对话消息详细
export function getChatMessage(id) {
  return request({
    url: '/gpt/chat-message/' + id,
    method: 'get'
  })
}

// 新增对话消息
export function addChatMessage(data) {
  return request({
    url: '/gpt/chat-message',
    method: 'post',
    data: data
  })
}

// 修改对话消息
export function updateChatMessage(data) {
  return request({
    url: '/gpt/chat-message',
    method: 'put',
    data: data
  })
}

// 删除对话消息
export function delChatMessage(id) {
  return request({
    url: '/gpt/chat-message/' + id,
    method: 'delete'
  })
}
