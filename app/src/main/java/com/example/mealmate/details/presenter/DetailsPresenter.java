package com.example.mealmate.details.presenter;

import com.example.mealmate.network.DailyMealResponse;

public interface DetailsPresenter {

    public  void getMealDetails(String id,   DailyMealResponse dailyMealResponse);

}
