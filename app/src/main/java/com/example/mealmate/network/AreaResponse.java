package com.example.mealmate.network;

import com.example.mealmate.home.model.Area;
import com.example.mealmate.home.model.DailyMeal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {

    @SerializedName("meals")
    public List<Area> areas;


    public List<Area> getAreas() {
        return areas;
    }
}
