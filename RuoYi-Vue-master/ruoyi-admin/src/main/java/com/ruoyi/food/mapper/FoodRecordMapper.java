package com.ruoyi.food.mapper;

import com.ruoyi.food.domain.FoodRecord;

import java.util.List;

/**
 * 食物摄入记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
public interface FoodRecordMapper 
{
    /**
     * 查询食物摄入记录
     * 
     * @param id 食物摄入记录主键
     * @return 食物摄入记录
     */
    public FoodRecord selectFoodRecordById(Long id);

    /**
     * 查询食物摄入记录列表
     * 
     * @param foodRecord 食物摄入记录
     * @return 食物摄入记录集合
     */
    public List<FoodRecord> selectFoodRecordList(FoodRecord foodRecord);

    /**
     * 新增食物摄入记录
     * 
     * @param foodRecord 食物摄入记录
     * @return 结果
     */
    public int insertFoodRecord(FoodRecord foodRecord);

    /**
     * 修改食物摄入记录
     * 
     * @param foodRecord 食物摄入记录
     * @return 结果
     */
    public int updateFoodRecord(FoodRecord foodRecord);

    /**
     * 删除食物摄入记录
     * 
     * @param id 食物摄入记录主键
     * @return 结果
     */
    public int deleteFoodRecordById(Long id);

    /**
     * 批量删除食物摄入记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFoodRecordByIds(Long[] ids);
}
