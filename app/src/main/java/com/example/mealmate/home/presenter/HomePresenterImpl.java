package com.example.mealmate.home.presenter;

import android.util.Log;

import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.home.view.HomeView;
import com.example.mealmate.model.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {

    MealsRepository mealsRepository;

    private static final String TAG = "HomePresenterImpl";

    HomeView homeView;

    public HomePresenterImpl(HomeView homeView, MealsRepository mealsRepository) {
        this.homeView = homeView;
        this.mealsRepository = mealsRepository;

    }


    @Override
    public void getMeal() {
        mealsRepository.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        dailyMeals -> {
                            if (dailyMeals != null) {
                                if (dailyMeals != null) {
                                    homeView.showData(dailyMeals);
                                    Log.d(TAG, "getMeal: Data received: " + dailyMeals);
                                }
                            }
                        },
                        error -> {
                            homeView.showErrorMsg("Failed to fetch meal");
                            Log.d(TAG, "Error fetching data: " + error);
                        }
                );
    }

    @Override
    public void getAllNationalMeals() {
        mealsRepository.getNational()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nationalResponse -> {
                            homeView.showNationalData(nationalResponse.getNationalMeals());
                            Log.i(TAG, "getAllNationalMeals: " + nationalResponse.nationalMeals.toString());
                        },
                        error -> {
                            homeView.showErrorMsg("Failed to fetch National Meals");
                            Log.i(TAG, "Error fetchingNational Meals: " + error);
                        }
                );
    }


    @Override
    public void addToFav(DetailedMeal detailedMeal) {

    }
}
