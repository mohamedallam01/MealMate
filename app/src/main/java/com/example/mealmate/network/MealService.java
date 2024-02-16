package com.example.mealmate.network;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface MealService {

    @GET("random.php")
    Observable<DailyMealResponse> getDailyMeal();

    @GET("list.php?a=list")
    Observable<AreaResponse> getArea();

}
