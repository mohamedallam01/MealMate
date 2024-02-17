package com.example.mealmate.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface MealDao {

    @Query("SELECT * FROM meals")
    Flowable<List<DailyMeal>> getDailyMeal();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal (DailyMeal dailyMeal);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeals (List<DailyMeal> dailyMeals);
    @Delete
    Completable deleteMeal (DailyMeal dailyMeal);
}
