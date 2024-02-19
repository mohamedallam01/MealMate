package com.example.mealmate.favorite.presenter;

import com.example.mealmate.details.model.DetailedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavoritesPresenter {

    public Flowable<List<DetailedMeal>> getFavorites();
    public void deleteMeal(DetailedMeal product);


}
