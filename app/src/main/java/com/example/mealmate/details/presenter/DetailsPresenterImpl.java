package com.example.mealmate.details.presenter;

import android.util.Log;

import com.example.mealmate.details.view.DetailsView;
import com.example.mealmate.model.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImpl implements DetailsPresenter{


    MealsRepository mealsRepository;

    private static final String TAG = "DetailsPresenterImpl";

   DetailsView detailsView;


    public DetailsPresenterImpl(DetailsView detailsView, MealsRepository mealsRepository) {
        this.detailsView = detailsView;
        this.mealsRepository = mealsRepository;

    }
    @Override
    public void getMealDetails(String id) {

        Log.i(TAG, "getMealDetails: " + id);
        mealsRepository.getDetailedMeal(id)
                .subscribeOn(Schedulers.io())
                .doOnNext(detailedMeals -> {
                            if (detailedMeals != null) {
                                if (detailedMeals != null) {
                                    detailsView.showDetails(detailedMeals);
                                    Log.d(TAG, "getMeal: Data received: " + detailedMeals);
                                }
                            }
                        }
                ).subscribe(detailedMeals -> {
                            // Process data as usual
                        },
                        error -> {
                            Log.e(TAG, "Error fetching meal details:", error);
                            detailsView.showErrorMsg("Failed to fetch meal");
                        });

    }
}
