package com.example.mealmate.details.view;

import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.home.model.NationalMeal;
import com.example.mealmate.network.DailyMealResponse;

import java.util.List;

public interface DetailsView {

    void showDetails(DailyMealResponse dailyMealResponse);

    void showErrorMsg(String error);
}
