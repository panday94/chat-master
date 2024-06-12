import request from '@/utils/request'

// 查询分页聊天摘要列表
export function pageChat(query) {
  return request({
    url: '/gpt/chat/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶聊天摘要列表
export function listChat(query) {
  return request({
    url: '/gpt/chat/list',
    method: 'get',
    params: query
  })
}

// 查询聊天摘要详细
export function getChat(id) {
  return request({
    url: '/gpt/chat/' + id,
    method: 'get'
  })
}

// 新增聊天摘要
export function addChat(data) {
  return request({
    url: '/gpt/chat',
    method: 'post',
    data: data
  })
}

// 修改聊天摘要
export function updateChat(data) {
  return request({
    url: '/gpt/chat',
    method: 'put',
    data: data
  })
}

// 删除聊天摘要
export function delChat(id) {
  return request({
    url: '/gpt/chat/' + id,
    method: 'delete'
  })
}
