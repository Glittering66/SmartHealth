package com.ruoyi.food.domain.dto;



import java.math.BigDecimal;
import java.util.List;

public class FoodNutritionDetailDto {

    /** 基础信息 */
    private Long foodId;
    private String foodName;
    private String foodGroup;

    /** 份量信息 */
    private List<FoodServingDto> servings;

    /** 宏量营养素 */
    private MacroNutritionDto macro;

    /** 脂肪酸 */
    private FattyAcidDto fattyAcids;

    /** 矿物质 */
    private MineralDto minerals;

    /** 维生素 */
    private VitaminDto vitamins;

    public BigDecimal getTotalWeightG() {
        return totalWeightG;
    }

    public void setTotalWeightG(BigDecimal totalWeightG) {
        this.totalWeightG = totalWeightG;
    }

    private BigDecimal totalWeightG;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public List<FoodServingDto> getServings() {
        return servings;
    }

    public void setServings(List<FoodServingDto> servings) {
        this.servings = servings;
    }

    public MacroNutritionDto getMacro() {
        return macro;
    }

    public void setMacro(MacroNutritionDto macro) {
        this.macro = macro;
    }

    public FattyAcidDto getFattyAcids() {
        return fattyAcids;
    }

    public void setFattyAcids(FattyAcidDto fattyAcids) {
        this.fattyAcids = fattyAcids;
    }

    public MineralDto getMinerals() {
        return minerals;
    }

    public void setMinerals(MineralDto minerals) {
        this.minerals = minerals;
    }

    public VitaminDto getVitamins() {
        return vitamins;
    }

    public void setVitamins(VitaminDto vitamins) {
        this.vitamins = vitamins;
    }
}