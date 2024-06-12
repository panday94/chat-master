import request from '@/utils/request'

// 查询字典数据列表
export function listData(query) {
  return request({
    url: '/dict/page',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
export function getData(dictCode) {
  return request({
    url: '/dict/' + dictCode,
    method: 'get'
  })
}

// 新增字典数据
export function addData(data) {
  return request({
    url: '/dict',
    method: 'post',
    data: data
  })
}

// 修改字典数据
export function updateData(data) {
  return request({
    url: '/dict',
    method: 'put',
    data: data
  })
}

// 删除字典数据
export function delData(dictCode) {
  return request({
    url: '/dict/' + dictCode,
    method: 'delete'
  })
}
