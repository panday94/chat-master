import request from '@/utils/request'

// 查询分页兑换码列表
export function pageRedemption(query) {
  return request({
    url: '/gpt/redemption/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶兑换码列表
export function listRedemption(query) {
  return request({
    url: '/gpt/redemption/list',
    method: 'get',
    params: query
  })
}

// 查询兑换码详细
export function getRedemption(id) {
  return request({
    url: '/gpt/redemption/' + id,
    method: 'get'
  })
}

// 新增兑换码
export function addRedemption(data) {
  return request({
    url: '/gpt/redemption',
    method: 'post',
    data: data
  })
}

// 修改兑换码
export function updateRedemption(data) {
  return request({
    url: '/gpt/redemption',
    method: 'put',
    data: data
  })
}

// 删除兑换码
export function delRedemption(id) {
  return request({
    url: '/gpt/redemption/' + id,
    method: 'delete'
  })
}
