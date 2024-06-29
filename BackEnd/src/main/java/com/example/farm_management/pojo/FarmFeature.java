package com.example.farm_management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmFeature {
    private Date date;
    private Integer moisture;
    private Integer nutrient;

    public Integer getNutrient() {
        return nutrient;
    }

    public void setNutrient(Integer nutrient) {
        this.nutrient = nutrient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMoisture() {
        return moisture;
    }

    public void setMoisture(Integer moisture) {
        this.moisture = moisture;
    }
}
