package com.ruoyi.food.domain.vo;

public class FoodMacroStatsVO {

    /** 食物总数 */
    private Integer total;

    /** 平均热量 */
    private Double avgCalories;

    /** 高蛋白食物数量 */
    private Integer highProteinCount;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getAvgCalories() {
        return avgCalories;
    }

    public void setAvgCalories(Double avgCalories) {
        this.avgCalories = avgCalories;
    }

    public Integer getHighProteinCount() {
        return highProteinCount;
    }

    public void setHighProteinCount(Integer highProteinCount) {
        this.highProteinCount = highProteinCount;
    }

    // getter / setter
}