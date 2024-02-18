package com.example.mealmate.network;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Observable<DailyMealResponse> getDailyMeal();

    @GET("list.php?a=list")
    Observable<AreaResponse> getArea();

    @GET("categories.php")
    Observable<CategoryResponse> getCategory();

    @GET("filter.php?a=Egyptian")
    Observable<NationalResponse> getNationalMeals();

    @GET("lookup.php")
    Observable<DailyMealResponse> getMealDetails(@Query("i") String mealId);

}
