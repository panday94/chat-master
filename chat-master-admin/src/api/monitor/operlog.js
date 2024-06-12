import request from '@/utils/request'

// 查询操作日志列表
export function list(query) {
  return request({
    url: '/sys-log/page',
    method: 'get',
    params: query
  })
}

// 删除操作日志
export function delOperlog(operId) {
  return request({
    url: '/sys-log/' + operId,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperlog() {
  return request({
    url: '/sys-log/clean',
    method: 'delete'
  })
}
