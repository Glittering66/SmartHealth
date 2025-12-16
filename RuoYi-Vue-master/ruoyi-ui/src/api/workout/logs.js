import request from '@/utils/request'

// 查询运动记录列表
export function listLogs(query) {
  return request({
    url: '/workout/logs/list',
    method: 'get',
    params: query
  })
}

// 查询运动记录详细
export function getLogs(logId) {
  return request({
    url: '/workout/logs/' + logId,
    method: 'get'
  })
}

// 新增运动记录
export function addLogs(data) {
  return request({
    url: '/workout/logs',
    method: 'post',
    data: data
  })
}

// 修改运动记录
export function updateLogs(data) {
  return request({
    url: '/workout/logs',
    method: 'put',
    data: data
  })
}

// 删除运动记录
export function delLogs(logId) {
  return request({
    url: '/workout/logs/' + logId,
    method: 'delete'
  })
}
