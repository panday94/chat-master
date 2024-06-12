import request from '@/utils/request'

// 查询分页内容管理列表
export function pageContent(query) {
  return request({
    url: '/gpt/content/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶内容管理列表
export function listContent(query) {
  return request({
    url: '/gpt/content/list',
    method: 'get',
    params: query
  })
}

// 查询内容管理详细
export function getContent(id) {
  return request({
    url: '/gpt/content/' + id,
    method: 'get'
  })
}

// 新增内容管理
export function addContent(data) {
  return request({
    url: '/gpt/content',
    method: 'post',
    data: data
  })
}

// 修改内容管理
export function updateContent(data) {
  return request({
    url: '/gpt/content',
    method: 'put',
    data: data
  })
}

// 删除内容管理
export function delContent(id) {
  return request({
    url: '/gpt/content/' + id,
    method: 'delete'
  })
}
