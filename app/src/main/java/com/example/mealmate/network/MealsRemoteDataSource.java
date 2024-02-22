package com.example.mealmate.network;

import io.reactivex.rxjava3.core.Observable;

public interface MealsRemoteDataSource {

 Observable<DailyMealResponse> getDailyMeal();
 Observable<CountriesResponse> getCountries();
 Observable<CategoryResponse> getCategories();
 Observable<NationalResponse> getNationalMeals();
 Observable<DetailedMealResponse> getMealDetails(String id);
 Observable<DetailedMealResponse> getMealSearch(String key);


}