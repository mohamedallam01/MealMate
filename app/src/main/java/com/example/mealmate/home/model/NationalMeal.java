package com.example.mealmate.home.model;

import com.google.gson.annotations.SerializedName;

public class NationalMeal {

    @SerializedName("strMeal")
    private String mealName;

    @SerializedName("strMealThumb")
    private String mealThumbUrl;

    @SerializedName("idMeal")
    private String mealId;

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealThumbUrl() {
        return mealThumbUrl;
    }

    public void setMealThumbUrl(String mealThumbUrl) {
        this.mealThumbUrl = mealThumbUrl;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
}

