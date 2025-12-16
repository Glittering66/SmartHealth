import request from '@/utils/request'

// 查询训练项目列表
export function listDetails(query) {
  return request({
    url: '/workout/details/list',
    method: 'get',
    params: query
  })
}

// 查询训练项目详细
export function getDetails(detailId) {
  return request({
    url: '/workout/details/' + detailId,
    method: 'get'
  })
}

// 新增训练项目
export function addDetails(data) {
  return request({
    url: '/workout/details',
    method: 'post',
    data: data
  })
}

// 修改训练项目
export function updateDetails(data) {
  return request({
    url: '/workout/details',
    method: 'put',
    data: data
  })
}

// 删除训练项目
export function delDetails(detailId) {
  return request({
    url: '/workout/details/' + detailId,
    method: 'delete'
  })
}
