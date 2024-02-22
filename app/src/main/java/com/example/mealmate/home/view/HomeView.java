package com.example.mealmate.home.view;

import com.example.mealmate.home.model.NationalMeal;

import java.util.List;

import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.home.model.NationalMeal;

import java.util.List;

public interface HomeView {

    void showData(List<DetailedMeal> detailedMeal);

    void showNationalData(List<NationalMeal> nationalMealList);

    void showErrorMsg(String error);
}
