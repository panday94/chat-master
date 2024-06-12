import request from '@/utils/request'

// 查询分页订单列表
export function pageOrder(query) {
  return request({
    url: '/gpt/order/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶订单列表
export function listOrder(query) {
  return request({
    url: '/gpt/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(id) {
  return request({
    url: '/gpt/order/' + id,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/gpt/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/gpt/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/gpt/order/' + id,
    method: 'delete'
  })
}
