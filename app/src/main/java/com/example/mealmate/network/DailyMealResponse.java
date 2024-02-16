package com.example.mealmate.network;

import com.example.mealmate.home.model.DailyMeal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyMealResponse {


    @SerializedName("meals")
    public   List<DailyMeal> dailyMeals;


    public List<DailyMeal> getDailyMeals() {
        return dailyMeals;
    }
}
