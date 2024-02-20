package com.example.mealmate.network;

import com.example.mealmate.details.model.DetailedMeal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.example.mealmate.details.model.DetailedMeal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyMealResponse {


    @SerializedName("meals")
    public   List<DetailedMeal> detailedMeals;


    public List<DetailedMeal> getDailyMeals() {
        return detailedMeals;
    }
}