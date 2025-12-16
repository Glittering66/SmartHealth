import request from '@/utils/request'

// 查询运动类型列表
export function listReference(query) {
  return request({
    url: '/workout/reference/list',
    method: 'get',
    params: query
  })
}

// 查询运动类型详细
export function getReference(exerciseId) {
  return request({
    url: '/workout/reference/' + exerciseId,
    method: 'get'
  })
}

// 新增运动类型
export function addReference(data) {
  return request({
    url: '/workout/reference',
    method: 'post',
    data: data
  })
}

// 修改运动类型
export function updateReference(data) {
  return request({
    url: '/workout/reference',
    method: 'put',
    data: data
  })
}

// 删除运动类型
export function delReference(exerciseId) {
  return request({
    url: '/workout/reference/' + exerciseId,
    method: 'delete'
  })
}
