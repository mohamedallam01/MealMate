package com.example.mealmate.model;

import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.network.AreaResponse;
import com.example.mealmate.network.CategoryResponse;
import com.example.mealmate.network.DetailedMealResponse;
import com.example.mealmate.network.NationalResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface MealsRepository {

    Flowable<List<DailyMeal>> getDailyMeal();
    void deleteMeal(DailyMeal dailyMeal);
    void insertMeal(DailyMeal dailyMeal);

    void insertMeals(List<DailyMeal> dailyMeal);

    Observable<AreaResponse> getArea();
    Observable<CategoryResponse> getCategory();
    Observable<NationalResponse> getNational();
    Flowable<List<DetailedMeal>> getDetailedMeal(String id);



}
