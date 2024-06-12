import request from '@/utils/request'

// 查询分页会员套餐列表
export function pageComb(query) {
  return request({
    url: '/gpt/comb/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶会员套餐列表
export function listComb(query) {
  return request({
    url: '/gpt/comb/list',
    method: 'get',
    params: query
  })
}

// 查询会员套餐详细
export function getComb(id) {
  return request({
    url: '/gpt/comb/' + id,
    method: 'get'
  })
}

// 新增会员套餐
export function addComb(data) {
  return request({
    url: '/gpt/comb',
    method: 'post',
    data: data
  })
}

// 修改会员套餐
export function updateComb(data) {
  return request({
    url: '/gpt/comb',
    method: 'put',
    data: data
  })
}

// 删除会员套餐
export function delComb(id) {
  return request({
    url: '/gpt/comb/' + id,
    method: 'delete'
  })
}
