package com.ruoyi.food.domain.dto;



public class FattyAcidDto {

    /** Omega-3 脂肪酸（g） */
    private Float omega3;

    public Float getOmega3() {
        return omega3;
    }

    public void setOmega3(Float omega3) {
        this.omega3 = omega3;
    }

    public Float getOmega6() {
        return omega6;
    }

    public void setOmega6(Float omega6) {
        this.omega6 = omega6;
    }

    public Float getDha() {
        return dha;
    }

    public void setDha(Float dha) {
        this.dha = dha;
    }

    public Float getEpa() {
        return epa;
    }

    public void setEpa(Float epa) {
        this.epa = epa;
    }

    public Float getMonoFat() {
        return monoFat;
    }

    public void setMonoFat(Float monoFat) {
        this.monoFat = monoFat;
    }

    public Float getPolyFat() {
        return polyFat;
    }

    public void setPolyFat(Float polyFat) {
        this.polyFat = polyFat;
    }

    /** Omega-6 脂肪酸（g） */
    private Float omega6;

    /** DHA（二十二碳六烯酸，g） */
    private Float dha;

    /** EPA（二十碳五烯酸，g） */
    private Float epa;

    /** 单不饱和脂肪（g） */
    private Float monoFat;

    /** 多不饱和脂肪（g） */
    private Float polyFat;
}
