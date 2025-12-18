package com.ruoyi.food.service;

import java.util.List;
import com.ruoyi.food.domain.NutrientsMacro;

/**
 * 食物营养素Service接口
 * 
 * @author ruoyi
 * @date 2025-12-17
 */
public interface INutrientsMacroService 
{
    /**
     * 查询食物营养素
     * 
     * @param foodId 食物营养素主键
     * @return 食物营养素
     */
    public NutrientsMacro selectNutrientsMacroByFoodId(Long foodId);

    /**
     * 查询食物营养素列表
     * 
     * @param nutrientsMacro 食物营养素
     * @return 食物营养素集合
     */
    public List<NutrientsMacro> selectNutrientsMacroList(NutrientsMacro nutrientsMacro);

    /**
     * 新增食物营养素
     * 
     * @param nutrientsMacro 食物营养素
     * @return 结果
     */
    public int insertNutrientsMacro(NutrientsMacro nutrientsMacro);

    /**
     * 修改食物营养素
     * 
     * @param nutrientsMacro 食物营养素
     * @return 结果
     */
    public int updateNutrientsMacro(NutrientsMacro nutrientsMacro);

    /**
     * 批量删除食物营养素
     * 
     * @param foodIds 需要删除的食物营养素主键集合
     * @return 结果
     */
    public int deleteNutrientsMacroByFoodIds(Long[] foodIds);

    /**
     * 删除食物营养素信息
     * 
     * @param foodId 食物营养素主键
     * @return 结果
     */
    public int deleteNutrientsMacroByFoodId(Long foodId);
}
