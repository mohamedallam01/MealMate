package com.example.mealmate.db;

import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MealsLocalDataSource {

    public Flowable<List<DailyMeal>> getDailyMeal();
    public void insertMeal(DailyMeal dailyMeal);
    public void deleteMeal(DailyMeal dailyMeal);
}
