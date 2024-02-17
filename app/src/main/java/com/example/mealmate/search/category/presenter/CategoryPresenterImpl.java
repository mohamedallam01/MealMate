package com.example.mealmate.search.category.presenter;

import android.util.Log;

import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.search.view.CategoryView;
import com.example.mealmate.search.country.view.CountryView;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryPresenterImpl implements CategoryPresenter {



    MealsRepository mealsRepository;

    private static final String TAG = "SearchPresenterImpl";


    CategoryView categoryView;

    public CategoryPresenterImpl(CategoryView categoryView, MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
        this.categoryView = categoryView;

    }

    @Override
    public void getAllCategories() {

        Log.i(TAG, "getAllCategories: ");
        mealsRepository.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                            categoryView.showCategoryData(categoryResponse.getCategories());
                            Log.i(TAG, "getAllCategories: " + categoryResponse.categories.toString());
                        },
                        error -> {
                            categoryView.showErrorMsg("Failed to fetch Categories");
                            Log.i(TAG, "Error fetching Categories: " + error);
                        }
                );

    }
}
