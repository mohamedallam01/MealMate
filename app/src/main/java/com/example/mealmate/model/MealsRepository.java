package com.example.mealmate.model;

import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.network.CountriesResponse;
import com.example.mealmate.network.CategoryResponse;
import com.example.mealmate.network.DetailedMealResponse;
import com.example.mealmate.network.NationalResponse;
import com.example.mealmate.search.country.model.Country;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface MealsRepository {

    void deleteDailyMeal(DetailedMeal detailedMeal);
    void insertDailyMeal(DetailedMeal detailedMeal);

    Flowable<List<DetailedMeal>> getDailyMeal();
    void deleteMeal(com.example.mealmate.details.model.DetailedMeal detailedMeal);
    void changeFavoriteState(com.example.mealmate.details.model.DetailedMeal detailedMeal);

    void insertMeals(List<DetailedMeal> detailedMeal);

    Observable<CountriesResponse> getCountries();
    Observable<CategoryResponse> getCategory();
    Observable<NationalResponse> getNational();
    Flowable<DetailedMeal> getSingleDetailMeals(String id);

    public Flowable<List<DetailedMeal>> getFavorites();

    Observable<DetailedMealResponse> getSearchByName(String query);




}
