package com.example.mealmate.network;

import com.example.mealmate.home.model.NationalMeal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NationalResponse {

    @SerializedName("meals")
    public List<NationalMeal> nationalMeals;


    public List<NationalMeal> getNationalMeals() {
        return nationalMeals;
    }
}
