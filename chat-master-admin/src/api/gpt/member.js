import request from '@/utils/request'

// 查询分页会员用户列表
export function pageMember(query) {
  return request({
    url: '/gpt/user/page',
    method: 'get',
    params: query
  })
}

// 查询部分耶会员用户列表
export function listMember(query) {
  return request({
    url: '/gpt/user/list',
    method: 'get',
    params: query
  })
}

// 查询会员用户详细
export function getMember(id) {
  return request({
    url: '/gpt/user/' + id,
    method: 'get'
  })
}

// 新增会员用户
export function addMember(data) {
  return request({
    url: '/gpt/user',
    method: 'post',
    data: data
  })
}

// 修改会员用户
export function updateMember(data) {
  return request({
    url: '/gpt/user',
    method: 'put',
    data: data
  })
}

// 删除会员用户
export function delMember(id) {
  return request({
    url: '/gpt/user/' + id,
    method: 'delete'
  })
}
