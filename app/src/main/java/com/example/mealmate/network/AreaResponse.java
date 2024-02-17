package com.example.mealmate.network;

import com.example.mealmate.search.country.model.Area;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {

    @SerializedName("meals")
    public List<Area> areas;


    public List<Area> getAreas() {
        return areas;
    }
}
