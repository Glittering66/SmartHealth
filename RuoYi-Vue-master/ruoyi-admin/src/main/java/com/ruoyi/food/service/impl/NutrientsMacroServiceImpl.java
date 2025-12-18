package com.ruoyi.food.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.food.mapper.NutrientsMacroMapper;
import com.ruoyi.food.domain.NutrientsMacro;
import com.ruoyi.food.service.INutrientsMacroService;

/**
 * 食物营养素Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
@Service
public class NutrientsMacroServiceImpl implements INutrientsMacroService 
{
    @Autowired
    private NutrientsMacroMapper nutrientsMacroMapper;

    /**
     * 查询食物营养素
     * 
     * @param foodId 食物营养素主键
     * @return 食物营养素
     */
    @Override
    public NutrientsMacro selectNutrientsMacroByFoodId(Long foodId)
    {
        return nutrientsMacroMapper.selectNutrientsMacroByFoodId(foodId);
    }

    /**
     * 查询食物营养素列表
     * 
     * @param nutrientsMacro 食物营养素
     * @return 食物营养素
     */
    @Override
    public List<NutrientsMacro> selectNutrientsMacroList(NutrientsMacro nutrientsMacro)
    {
        return nutrientsMacroMapper.selectNutrientsMacroList(nutrientsMacro);
    }

    /**
     * 新增食物营养素
     * 
     * @param nutrientsMacro 食物营养素
     * @return 结果
     */
    @Override
    public int insertNutrientsMacro(NutrientsMacro nutrientsMacro)
    {
        return nutrientsMacroMapper.insertNutrientsMacro(nutrientsMacro);
    }

    /**
     * 修改食物营养素
     * 
     * @param nutrientsMacro 食物营养素
     * @return 结果
     */
    @Override
    public int updateNutrientsMacro(NutrientsMacro nutrientsMacro)
    {
        return nutrientsMacroMapper.updateNutrientsMacro(nutrientsMacro);
    }

    /**
     * 批量删除食物营养素
     * 
     * @param foodIds 需要删除的食物营养素主键
     * @return 结果
     */
    @Override
    public int deleteNutrientsMacroByFoodIds(Long[] foodIds)
    {
        return nutrientsMacroMapper.deleteNutrientsMacroByFoodIds(foodIds);
    }

    /**
     * 删除食物营养素信息
     * 
     * @param foodId 食物营养素主键
     * @return 结果
     */
    @Override
    public int deleteNutrientsMacroByFoodId(Long foodId)
    {
        return nutrientsMacroMapper.deleteNutrientsMacroByFoodId(foodId);
    }
}
