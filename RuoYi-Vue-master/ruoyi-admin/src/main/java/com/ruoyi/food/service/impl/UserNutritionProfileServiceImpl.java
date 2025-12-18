package com.ruoyi.food.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.food.mapper.UserNutritionProfileMapper;
import com.ruoyi.food.domain.UserNutritionProfile;
import com.ruoyi.food.service.IUserNutritionProfileService;

/**
 * 用户营养计算基础信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-18
 */
@Service
public class UserNutritionProfileServiceImpl implements IUserNutritionProfileService 
{
    @Autowired
    private UserNutritionProfileMapper userNutritionProfileMapper;

    /**
     * 查询用户营养计算基础信息
     * 
     * @param id 用户营养计算基础信息主键
     * @return 用户营养计算基础信息
     */
    @Override
    public UserNutritionProfile selectUserNutritionProfileById(Long id)
    {
        return userNutritionProfileMapper.selectUserNutritionProfileById(id);
    }

    /**
     * 查询用户营养计算基础信息列表
     * 
     * @param userNutritionProfile 用户营养计算基础信息
     * @return 用户营养计算基础信息
     */
    @Override
    public List<UserNutritionProfile> selectUserNutritionProfileList(UserNutritionProfile userNutritionProfile)
    {
        return userNutritionProfileMapper.selectUserNutritionProfileList(userNutritionProfile);
    }

    /**
     * 新增用户营养计算基础信息
     * 
     * @param userNutritionProfile 用户营养计算基础信息
     * @return 结果
     */
    @Override
    public int insertUserNutritionProfile(UserNutritionProfile userNutritionProfile)
    {
        userNutritionProfile.setCreateTime(DateUtils.getNowDate());
        return userNutritionProfileMapper.insertUserNutritionProfile(userNutritionProfile);
    }

    /**
     * 修改用户营养计算基础信息
     * 
     * @param userNutritionProfile 用户营养计算基础信息
     * @return 结果
     */
    @Override
    public int updateUserNutritionProfile(UserNutritionProfile userNutritionProfile)
    {
        userNutritionProfile.setUpdateTime(DateUtils.getNowDate());
        return userNutritionProfileMapper.updateUserNutritionProfile(userNutritionProfile);
    }

    /**
     * 批量删除用户营养计算基础信息
     * 
     * @param ids 需要删除的用户营养计算基础信息主键
     * @return 结果
     */
    @Override
    public int deleteUserNutritionProfileByIds(Long[] ids)
    {
        return userNutritionProfileMapper.deleteUserNutritionProfileByIds(ids);
    }

    /**
     * 删除用户营养计算基础信息信息
     * 
     * @param id 用户营养计算基础信息主键
     * @return 结果
     */
    @Override
    public int deleteUserNutritionProfileById(Long id)
    {
        return userNutritionProfileMapper.deleteUserNutritionProfileById(id);
    }
}
