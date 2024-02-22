package com.example.mealmate.details.view;

import com.example.mealmate.details.model.DetailedMeal;

public interface DetailsView {

    void showDetails(DetailedMeal detailedMealList);

    void showErrorMsg(String error);
}