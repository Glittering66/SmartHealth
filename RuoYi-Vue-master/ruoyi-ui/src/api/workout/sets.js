import request from '@/utils/request'

// 查询组列表
export function listSets(detailId) {
  return request({
    url: '/workout/sets/list',
    method: 'get',
    params: { detailId }
  })
}

// 新增组
export function addSet(data) {
  return request({
    url: '/workout/sets',
    method: 'post',
    data
  })
}

// 修改组
export function updateSet(data) {
  return request({
    url: '/workout/sets',
    method: 'put',
    data
  })
}

// 删除组
export function deleteSetById(setId) {
  return request({
    url: `/workout/sets/${setId}`,
    method: 'delete'
  })
}

// 根据logId查询所有组记录
export function listSetsByLogId(logId) {
  return request({
    url: '/api/workout/sets/listByLogId',
    method: 'get',
    params: { logId }
  })
}
