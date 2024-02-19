package com.example.mealmate.details.presenter;

import com.example.mealmate.details.model.DetailedMeal;

public interface DetailsPresenter {

      void getMealDetails(String id);

      public void addToFav(DetailedMeal detailedMeal);

}
