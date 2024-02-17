package com.example.mealmate.home.presenter;

import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface HomePresenter {

    void getMeal();


    void addToFav(DailyMeal dailyMeal);
}
