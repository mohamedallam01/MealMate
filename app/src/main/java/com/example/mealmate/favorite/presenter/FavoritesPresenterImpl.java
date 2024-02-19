package com.example.mealmate.favorite.presenter;

import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.model.MealsRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavoritesPresenterImpl implements FavoritesPresenter{

    //FavProductsView favProductsView;
    MealsRepository mealsRepository;
    List<DetailedMeal> detailedMealList;

    public FavoritesPresenterImpl(MealsRepository mealsRepository){
        this.mealsRepository = mealsRepository;

    }
    @Override
    public Flowable<List<DetailedMeal>> getFavorites() {
        return mealsRepository.getFavorites();
    }

    @Override
    public void deleteMeal(DetailedMeal detailedMeal) {

        mealsRepository.deleteMeal(detailedMeal);

    }
}
