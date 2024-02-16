package com.example.mealmate.home.view;

import com.example.mealmate.home.model.Area;
import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface HomeView {

    void showData(List<DailyMeal> dailyMeal);
    void showAreaData(List<Area> areaList);

    void showErrorMsg(String error);
}
