import request from '@/utils/request'

// 查询项目组数列表
export function listSets(query) {
  return request({
    url: '/workout/sets/list',
    method: 'get',
    params: query
  })
}

// 查询项目组数详细
export function getSets(setId) {
  return request({
    url: '/workout/sets/' + setId,
    method: 'get'
  })
}

// 新增项目组数
export function addSets(data) {
  return request({
    url: '/workout/sets',
    method: 'post',
    data: data
  })
}

// 修改项目组数
export function updateSets(data) {
  return request({
    url: '/workout/sets',
    method: 'put',
    data: data
  })
}

// 删除项目组数
export function delSets(setId) {
  return request({
    url: '/workout/sets/' + setId,
    method: 'delete'
  })
}
