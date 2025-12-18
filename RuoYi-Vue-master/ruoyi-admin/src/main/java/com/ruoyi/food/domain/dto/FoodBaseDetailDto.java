package com.ruoyi.food.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class FoodBaseDetailDto {
    private Long foodId;
    private String foodName;
    private String foodGroup;
    private List<FoodServingDto> servings;
}
