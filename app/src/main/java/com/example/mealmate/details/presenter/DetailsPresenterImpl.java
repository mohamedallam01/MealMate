package com.example.mealmate.details.presenter;

import android.util.Log;

import com.example.mealmate.details.view.DetailsView;
import com.example.mealmate.home.view.HomeView;
import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.network.DailyMealResponse;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImpl implements DetailsPresenter{


    MealsRepository mealsRepository;

    private static final String TAG = "DetailsPresenterImpl";

   DetailsView detailsView;

   DailyMealResponse dailyMealResponse;

    public DetailsPresenterImpl(DetailsView detailsView, MealsRepository mealsRepository) {
        this.detailsView = detailsView;
        this.mealsRepository = mealsRepository;

    }
    @Override
    public void getMealDetails(String id, DailyMealResponse dailyMealResponse) {

        mealsRepository.getMealDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        mealDetails -> {
                            if (mealDetails != null) {
                                if (mealDetails != null) {
                                    detailsView.showDetails(dailyMealResponse);
                                    Log.i(TAG, "getMeal: Data received: " + mealDetails);
                                }
                            }
                        },
                        error -> {
                            detailsView.showErrorMsg("Failed to fetch meal");
                            Log.i(TAG, "Error fetching data: " + error);
                        }
                );



    }
}
