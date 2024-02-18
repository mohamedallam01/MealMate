package com.example.mealmate.db;

import android.content.Context;
import android.util.Log;

import com.example.mealmate.home.model.DailyMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {


    private MealDao mealDao;

    private static final String TAG = "MealsLocalDataSourceImpl";

    private static MealsLocalDataSourceImpl localSource = null;

    private Flowable<List<DailyMeal>> dailyMeals;

    public MealsLocalDataSourceImpl(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDao = db.getMealDao();
        dailyMeals = mealDao.getDailyMeal();
    }

    public static MealsLocalDataSourceImpl getInstance(Context context) {
        Log.d("LOCALDataSource", "getInstance");
        if (localSource == null) {
            localSource = new MealsLocalDataSourceImpl(context);
        }
        return localSource;
    }

    @Override
    public Flowable<List<DailyMeal>> getDailyMeal() {

        return dailyMeals;
    }

    @Override
    public void insertMeal(DailyMeal dailyMeal) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                mealDao.insertMeal(dailyMeal);
            }
        }).start();

    }

    @Override
    public void insertMeals(List<DailyMeal> dailyMeals) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mealDao.insertMeals(dailyMeals);
//            }
//        }).start();

//        if (dailyMeals != null) {
//            Log.d(TAG, "insertMeals: " + dailyMeals.size());
//
//            for (DailyMeal meal : dailyMeals) {
//
//                if (meal != null) {
//                    Log.d(TAG, "Meal ID: " + meal.getIdMeal());
//                } else {
//                    Log.d(TAG, "attempted to insert null meals");
//                }
//
//
//            }
//            Completable.fromRunnable(new Runnable() {
//                        @Override
//                        public void run() {
//                            mealDao.insertMeals(dailyMeals);
//                        }
//                    })
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(() -> {
//                                Log.d(TAG, "insertMeals: Successful ");
//                            },
//                            throwable -> {
//                                Log.d(TAG, "insertMeals: Failed: " + throwable);
//                            });
//        } else {
//            Log.d(TAG, "insertMeals: failed : Attempt to insert null list of daily meals");
//        }


    }

    @Override
    public void deleteMeal(DailyMeal dailyMeal) {

    }
}



