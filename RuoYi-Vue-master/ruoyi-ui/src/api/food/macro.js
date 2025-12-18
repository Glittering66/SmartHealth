import request from '@/utils/request'

// 查询食物营养素列表
export function listMacro(query) {
  return request({
    url: '/food/macro/list',
    method: 'get',
    params: query
  })
}

// 查询食物营养素详细
export function getMacro(foodId) {
  return request({
    url: '/food/macro/' + foodId,
    method: 'get'
  })
}

// 新增食物营养素
export function addMacro(data) {
  return request({
    url: '/food/macro',
    method: 'post',
    data: data
  })
}

// 修改食物营养素
export function updateMacro(data) {
  return request({
    url: '/food/macro',
    method: 'put',
    data: data
  })
}

// 删除食物营养素
export function delMacro(foodId) {
  return request({
    url: '/food/macro/' + foodId,
    method: 'delete'
  })
}

export function listMacroByGroup(foodGroup, params) {
  return request({
    url: '/food/macro/group/list',
    method: 'get',
    params: {
      foodGroup,
      ...params
    }
  })
}
// 新增：按食物名称查询
export function listMacroByName(foodName, params) {
  return request({
    url: '/food/macro/name/list',
    method: 'get',
    params: {
      foodName,
      ...params
    }
  })
}

// 新增：按分类和名称组合查询
export function listMacroByCombo(query, params) {
  return request({
    url: '/food/macro/combo/list',
    method: 'get',
    params: {
      ...query,
      ...params
    }
  })
}
