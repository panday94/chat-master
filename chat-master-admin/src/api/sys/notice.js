import request from '@/utils/request'

// 查询公告列表
export function listNotice(query) {
  return request({
    url: '/notice/page',
    method: 'get',
    params: query
  })
}

// 根据登录账号获取系统通知数据
export function listNoticeBySysUser(query) {
  return request({
    url: '/notice/read/page',
    method: 'get',
    params: query
  })
}

// 根据登录账号获取系统通知数据
export function getUnreadCount() {
  return request({
    url: '/notice/unread/count',
    method: 'get'
  })
}

// 查询公告详细
export function getNotice(noticeId) {
  return request({
    url: '/notice/' + noticeId,
    method: 'get'
  })
}

// 新增公告
export function addNotice(data) {
  return request({
    url: '/notice',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateNotice(data) {
  return request({
    url: '/notice',
    method: 'put',
    data: data
  })
}

// 已读公告
export function readNotice(data) {
  return request({
    url: '/notice/read',
    method: 'put',
    data: data
  })
}

// 一键已读公告
export function readNoticeAll() {
  return request({
    url: '/notice/read/all',
    method: 'put',
  })
}

// 删除公告
export function delNotice(noticeId) {
  return request({
    url: '/notice/' + noticeId,
    method: 'delete'
  })
}