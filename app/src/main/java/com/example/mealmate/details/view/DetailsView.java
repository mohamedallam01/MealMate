package com.example.mealmate.details.view;

import com.example.mealmate.details.model.DetailedMeal;

import java.util.List;

public interface DetailsView {

    void showDetails(List<DetailedMeal> detailedMealList);

    void showErrorMsg(String error);
}
