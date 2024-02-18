package com.example.mealmate.network;

import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsRemoteDataSource {

 Observable<DailyMealResponse> getDailyMeal();
 Observable<AreaResponse> getAreas();
 Observable<CategoryResponse> getCategories();
 Observable<NationalResponse> getNationalMeals();
 Observable<DailyMealResponse> getMealDetails(String id);

}
