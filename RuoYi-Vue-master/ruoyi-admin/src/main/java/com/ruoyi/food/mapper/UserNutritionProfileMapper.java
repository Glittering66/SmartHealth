package com.ruoyi.food.mapper;

import java.util.List;
import com.ruoyi.food.domain.UserNutritionProfile;

/**
 * 用户营养计算基础信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-18
 */
public interface UserNutritionProfileMapper 
{
    /**
     * 查询用户营养计算基础信息
     * 
     * @param id 用户营养计算基础信息主键
     * @return 用户营养计算基础信息
     */
    public UserNutritionProfile selectUserNutritionProfileById(Long id);

    /**
     * 查询用户营养计算基础信息列表
     * 
     * @param userNutritionProfile 用户营养计算基础信息
     * @return 用户营养计算基础信息集合
     */
    public List<UserNutritionProfile> selectUserNutritionProfileList(UserNutritionProfile userNutritionProfile);

    /**
     * 新增用户营养计算基础信息
     * 
     * @param userNutritionProfile 用户营养计算基础信息
     * @return 结果
     */
    public int insertUserNutritionProfile(UserNutritionProfile userNutritionProfile);

    /**
     * 修改用户营养计算基础信息
     * 
     * @param userNutritionProfile 用户营养计算基础信息
     * @return 结果
     */
    public int updateUserNutritionProfile(UserNutritionProfile userNutritionProfile);

    /**
     * 删除用户营养计算基础信息
     * 
     * @param id 用户营养计算基础信息主键
     * @return 结果
     */
    public int deleteUserNutritionProfileById(Long id);

    /**
     * 批量删除用户营养计算基础信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserNutritionProfileByIds(Long[] ids);
}
