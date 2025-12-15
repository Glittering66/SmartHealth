package com.ruoyi.good.service;

import java.util.List;
import com.ruoyi.good.domain.Good;
import com.ruoyi.good.domain.vo.Goodvo;

/**
 * 管理商品Service接口
 * 
 * @author ruoyi
 * @date 2025-12-15
 */
public interface IGoodService 
{
    /**
     * 查询管理商品
     * 
     * @param goodId 管理商品主键
     * @return 管理商品
     */
    public Good selectGoodByGoodId(Long goodId);

    /**
     * 查询管理商品列表
     * 
     * @param good 管理商品
     * @return 管理商品集合
     */
    public List<Good> selectGoodList(Good good);

    /**
     * 新增管理商品
     * 
     * @param good 管理商品
     * @return 结果
     */
    public int insertGood(Good good);

    /**
     * 修改管理商品
     * 
     * @param good 管理商品
     * @return 结果
     */
    public int updateGood(Good good);

    /**
     * 批量删除管理商品
     * 
     * @param goodIds 需要删除的管理商品主键集合
     * @return 结果
     */
    public int deleteGoodByGoodIds(Long[] goodIds);

    /**
     * 删除管理商品信息
     * 
     * @param goodId 管理商品主键
     * @return 结果
     */
    public int deleteGoodByGoodId(Long goodId);

    /**
     *查询商品
     * @param good  商品
     * @return   带有name的商品
     */
    public List<Goodvo> selectGoodvoList(Good good);

}
