package com.example.mealmate.network;

import com.example.mealmate.search.country.model.Country;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {

    @SerializedName("meals")
    public List<Country> countries;


    public List<Country> getCountries() {
        return countries;
    }
}
