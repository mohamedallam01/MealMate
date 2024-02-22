package com.example.mealmate.home.presenter;

import com.example.mealmate.details.model.DetailedMeal;

public interface HomePresenter {

    void getMeal();

    void getAllNationalMeals();

    void addToFav(DetailedMeal detailedMeal);
}
