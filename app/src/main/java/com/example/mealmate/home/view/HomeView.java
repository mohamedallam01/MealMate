package com.example.mealmate.home.view;

import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

public interface HomeView {

    void showData(List<DailyMeal> dailyMeal);


    void showErrorMsg(String error);
}
