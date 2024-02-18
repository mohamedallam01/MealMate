package com.example.mealmate.home.view;

import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.home.model.NationalMeal;
import com.example.mealmate.search.country.model.Area;

import java.util.List;

public interface HomeView {

    void showData(List<DailyMeal> dailyMeal);

    void showNationalData(List<NationalMeal> nationalMealList);

    void showErrorMsg(String error);
}
