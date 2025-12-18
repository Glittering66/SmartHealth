package com.ruoyi.food.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.food.domain.FoodRecord;
import com.ruoyi.food.mapper.FoodRecordMapper;
import com.ruoyi.food.service.IFoodRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 食物摄入记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
@Service
public class FoodRecordServiceImpl implements IFoodRecordService 
{
    @Autowired
    private FoodRecordMapper foodRecordMapper;

    /**
     * 查询食物摄入记录
     * 
     * @param id 食物摄入记录主键
     * @return 食物摄入记录
     */
    @Override
    public FoodRecord selectFoodRecordById(Long id)
    {
        return foodRecordMapper.selectFoodRecordById(id);
    }

    /**
     * 查询食物摄入记录列表
     * 
     * @param foodRecord 食物摄入记录
     * @return 食物摄入记录
     */
    @Override
    public List<FoodRecord> selectFoodRecordList(FoodRecord foodRecord)
    {
        return foodRecordMapper.selectFoodRecordList(foodRecord);
    }

    /**
     * 新增食物摄入记录
     * 
     * @param foodRecord 食物摄入记录
     * @return 结果
     */
    @Override
    public int insertFoodRecord(FoodRecord foodRecord)
    {
        foodRecord.setCreateTime(DateUtils.getNowDate());
        return foodRecordMapper.insertFoodRecord(foodRecord);
    }

    /**
     * 修改食物摄入记录
     * 
     * @param foodRecord 食物摄入记录
     * @return 结果
     */
    @Override
    public int updateFoodRecord(FoodRecord foodRecord)
    {
        foodRecord.setUpdateTime(DateUtils.getNowDate());
        return foodRecordMapper.updateFoodRecord(foodRecord);
    }

    /**
     * 批量删除食物摄入记录
     * 
     * @param ids 需要删除的食物摄入记录主键
     * @return 结果
     */
    @Override
    public int deleteFoodRecordByIds(Long[] ids)
    {
        return foodRecordMapper.deleteFoodRecordByIds(ids);
    }

    /**
     * 删除食物摄入记录信息
     * 
     * @param id 食物摄入记录主键
     * @return 结果
     */
    @Override
    public int deleteFoodRecordById(Long id)
    {
        return foodRecordMapper.deleteFoodRecordById(id);
    }
}
