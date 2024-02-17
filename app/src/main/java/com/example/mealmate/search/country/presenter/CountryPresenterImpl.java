package com.example.mealmate.search.country.presenter;

import android.util.Log;

import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.search.view.CategoryView;
import com.example.mealmate.search.country.view.CountryView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountryPresenterImpl implements CountryPresenter {



    MealsRepository mealsRepository;

    private static final String TAG = "SearchPresenterImpl";

    CountryView countryView;

    CategoryView categoryView;

    public CountryPresenterImpl(CountryView countryView, MealsRepository mealsRepository) {
        this.countryView = countryView;
        this.mealsRepository = mealsRepository;


    }

    @Override
    public void getAllCountries() {
        Log.i(TAG, "getAllCountries: ");
        mealsRepository.getArea()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(areaResponse -> {
                            countryView.showAreaData(areaResponse.getAreas());
                            Log.i(TAG, "getAllCountries: " + areaResponse.areas.toString());
                        },
                        error -> {
                            countryView.showErrorMsg("Failed to fetch Areas");
                            Log.i(TAG, "Error fetching Areas: " + error);
                        }
                );
    }
}
