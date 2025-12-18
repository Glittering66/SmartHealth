import request from '@/utils/request'

// 查询食物查询列表
export function listFood(query) {
  return request({
    url: '/food/food/list',
    method: 'get',
    params: query
  })
}

// 查询食物查询详细
export function getFood(id) {
  return request({
    url: '/food/food/' + id,
    method: 'get'
  })
}

// 新增食物查询
export function addFood(data) {
  return request({
    url: '/food/food',
    method: 'post',
    data: data
  })
}

// 修改食物查询
export function updateFood(data) {
  return request({
    url: '/food/food',
    method: 'put',
    data: data
  })
}

// 删除食物查询
export function delFood(id) {
  return request({
    url: '/food/food/' + id,
    method: 'delete'
  })
}

export function getFoodGroupStats() {
  return request({
    url: '/food/food/group/stats',
    method: 'get'
  })
}

export function getFoodDetail(id) {
  return request({
    url: `/food/food/detail/${id}`,
    method: 'get'
  })
}
