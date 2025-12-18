import request from '@/utils/request'

// 根据 logId 查询训练项目列表
export function listDetails(logId) {
  return request({
    url: '/workout/details/list',
    method: 'get',
    params: { logId }
  })
}

// 查询训练项目详细
export function getDetails(detailId) {
  return request({
    url: `/workout/details/${detailId}`,
    method: 'get'
  })
}

// 新增训练项目
export function addDetails(data) {
  return request({
    url: '/workout/details',
    method: 'post',
    data
  })
}

// 修改训练项目
export function updateDetails(data) {
  return request({
    url: '/workout/details',
    method: 'put',
    data
  })
}

// ⭐ 级联删除（正确用这个）
export function deleteDetailCascade(detailId) {
  return request({
    url: `/workout/details/cascade/${detailId}`,
    method: 'delete'
  })
}
