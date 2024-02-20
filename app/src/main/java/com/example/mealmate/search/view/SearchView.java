package com.example.mealmate.search.view;

import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.network.DetailedMealResponse;

import java.util.List;

public interface SearchView {

    void showSearchResults(List<DetailedMeal> detailedMealList);
    void showError(String errorMessage);
}
