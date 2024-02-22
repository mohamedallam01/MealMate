package com.example.mealmate.network;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {

    @GET("random.php")
    Observable<DailyMealResponse> getDailyMeal();

    @GET("list.php?a=list")
    Observable<CountriesResponse> getArea();

    @GET("categories.php")
    Observable<CategoryResponse> getCategory();

    @GET("filter.php?a=Egyptian")
    Observable<NationalResponse> getNationalMeals();

    @GET("lookup.php?i=")
    Observable<DetailedMealResponse> getMealDetails(@Query("i") String mealId);


    @GET("search.php?s=")
    Single<DetailedMealResponse> getSearchByName(@Query("s") String mealName);

}
