package com.example.mealmate.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealmate.details.model.DetailedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(DetailedMeal detailedMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeals(List<DetailedMeal> detailedMeals);

    @Query("SELECT * FROM detailed_meal_table")
    Flowable<List<DetailedMeal>> getDailyMeal();

    @Query("SELECT * FROM detailed_meal_table WHERE idMeal = :id LIMIT 1")
    Flowable<DetailedMeal> getMealById(String id);

    @Delete
    void deleteMeal(DetailedMeal detailedMeal);


    @Query("SELECT * FROM detailed_meal_table WHERE isFavorite = true")
    Flowable<List<DetailedMeal>> getFavorites();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavorite(DetailedMeal detailedMeal);


    @Query("SELECT COUNT(*) FROM detailed_meal_table WHERE idMeal = :mealId")
    Flowable<Integer> countMealById(String mealId);
}
