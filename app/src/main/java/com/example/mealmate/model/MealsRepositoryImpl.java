package com.example.mealmate.model;

import android.util.Log;

import androidx.room.RoomDatabase;

import com.example.mealmate.db.AppDataBase;
import com.example.mealmate.db.MealsLocalDataSource;
import com.example.mealmate.home.model.Area;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.network.AreaResponse;
import com.example.mealmate.network.DailyMealResponse;
import com.example.mealmate.network.MealService;
import com.example.mealmate.network.MealsRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepositoryImpl implements MealsRepository {

    private static final String TAG = "MealsRepositoryImpl";

    MealsLocalDataSource localDataSource;
    MealsRemoteDataSource remoteDataSource;

    MealService mealService;

    private static MealsRepositoryImpl mealsRepository = null;

    private MealsRepositoryImpl(MealsLocalDataSource mealsLocalDataSource, MealsRemoteDataSource mealsRemoteDataSource) {

        this.localDataSource = mealsLocalDataSource;
        this.remoteDataSource = mealsRemoteDataSource;

    }

    public static MealsRepositoryImpl getInstance(MealsLocalDataSource mealsLocalDataSource, MealsRemoteDataSource mealsRemoteDataSource) {
        if (mealsRepository == null) {
            mealsRepository = new MealsRepositoryImpl(mealsLocalDataSource, mealsRemoteDataSource);

        }

        return mealsRepository;
    }


    @Override
    public Flowable<List<DailyMeal>> getDailyMeal() {
        Log.i(TAG, "getDailyMeal: get meal from remote" + remoteDataSource.getDailyMeal().toString());

        return remoteDataSource.getDailyMeal().flatMapIterable(DailyMealResponse::getDailyMeals)
                .doOnNext( dailyMeal ->{
                            Log.i(TAG, "getDailyMeal: get meal from remote" + remoteDataSource.getDailyMeal().toString());
                            insertMeal(dailyMeal);
                            Log.i(TAG, "getDailyMeal: meal inserted to database" + remoteDataSource.getDailyMeal().toString());



                        }


                        )
                .toList()
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteMeal(DailyMeal dailyMeal) {

    }

    @Override
    public void insertMeal(DailyMeal dailyMeal) {
        Log.i(TAG, "Inserting meal: " + dailyMeal.getIdMeal());
        localDataSource.insertMeal(dailyMeal);

    }

    @Override
    public Observable<AreaResponse> getArea() {
        return remoteDataSource.getAreas()
                .subscribeOn(Schedulers.io());
    }
}
