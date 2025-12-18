import request from '@/utils/request'

// 查询食物摄入记录列表
export function listRecord(query) {
  return request({
    url: '/food/record/list',
    method: 'get',
    params: query
  })
}

// 查询食物摄入记录详细
export function getRecord(id) {
  return request({
    url: '/food/record/' + id,
    method: 'get'
  })
}

// 新增食物摄入记录
export function addRecord(data) {
  return request({
    url: '/food/record',
    method: 'post',
    data: data
  })
}

// 修改食物摄入记录
export function updateRecord(data) {
  return request({
    url: '/food/record',
    method: 'put',
    data: data
  })
}

// 删除食物摄入记录
export function delRecord(id) {
  return request({
    url: '/food/record/' + id,
    method: 'delete'
  })
}
