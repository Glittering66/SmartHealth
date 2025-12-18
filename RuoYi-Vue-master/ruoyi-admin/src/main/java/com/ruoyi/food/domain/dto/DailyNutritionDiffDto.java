package com.ruoyi.food.domain.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class DailyNutritionDiffDto {

    /** 查询日期 yyyy-MM-dd */
    private String date;

    /** 实际摄入 */
    private Map<String, BigDecimal> actual;

    /** 推荐摄入 */
    private Map<String, BigDecimal> recommended;

    /** 差值（recommended - actual） */
    private Map<String, BigDecimal> diff;
}
