package com.example.mealmate.network;

import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.core.Observable;

public interface MealsRemoteDataSource {

 Observable<DailyMealResponse> getDailyMeal();
 Observable<AreaResponse> getAreas();
 Observable<CategoryResponse> getCategories();
 Observable<NationalResponse> getNationalMeals();
 Observable<DetailedMealResponse> getMealDetails(String id);

}