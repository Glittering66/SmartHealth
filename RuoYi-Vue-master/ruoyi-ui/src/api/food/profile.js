import request from '@/utils/request'

// 查询用户营养计算基础信息列表
export function listProfile(query) {
  return request({
    url: '/food/profile/list',
    method: 'get',
    params: query
  })
}

// 查询用户营养计算基础信息详细
export function getProfile(id) {
  return request({
    url: '/food/profile/' + id,
    method: 'get'
  })
}

// 新增用户营养计算基础信息
export function addProfile(data) {
  return request({
    url: '/food/profile',
    method: 'post',
    data: data
  })
}

// 修改用户营养计算基础信息
export function updateProfile(data) {
  return request({
    url: '/food/profile',
    method: 'put',
    data: data
  })
}

// 删除用户营养计算基础信息
export function delProfile(id) {
  return request({
    url: '/food/profile/' + id,
    method: 'delete'
  })
}
