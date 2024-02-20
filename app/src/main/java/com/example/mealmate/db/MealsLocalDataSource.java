package com.example.mealmate.db;

import com.example.mealmate.details.model.DetailedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MealsLocalDataSource {

    public void insertMeal(DetailedMeal detailedMeal);

    public void insertMeals(List<DetailedMeal> detailedMeal);

    public void changeFavoriteState(DetailedMeal detailedMeal);

    public Flowable<List<DetailedMeal>> getFavorites();

    public Flowable<List<DetailedMeal>> getDetailedMeals();
    public Flowable<DetailedMeal> getSingleDetailedMeals(String id);


    public void deleteMeal(DetailedMeal detailedMeal);


}