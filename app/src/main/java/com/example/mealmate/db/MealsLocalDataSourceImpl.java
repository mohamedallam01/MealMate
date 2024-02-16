package com.example.mealmate.db;

import android.content.Context;

import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.network.DailyMealResponse;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {


    private MealDao mealDao;

    private static MealsLocalDataSourceImpl localSource = null;

    private Flowable<List<DailyMeal>> dailyMeal;

    public MealsLocalDataSourceImpl(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDao = db.getMealDao();
        //dailyMeal = mealDao.getDailyMeal();
    }

    public static MealsLocalDataSourceImpl getInstance(Context context) {
        if (localSource == null) {
            localSource = new MealsLocalDataSourceImpl(context);
        }
        return localSource;
    }

    @Override
    public Flowable<List<DailyMeal>> getDailyMeal() {

        return dailyMeal;
    }

    @Override
    public void insertMeal(DailyMeal dailyMeal) {
        mealDao.insertMeal(dailyMeal);

    }

    @Override
    public void deleteMeal(DailyMeal dailyMeal) {

    }
}
