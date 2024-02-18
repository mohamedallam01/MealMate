package com.example.mealmate.model;

import android.util.Log;

import com.example.mealmate.db.MealsLocalDataSource;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.network.AreaResponse;
import com.example.mealmate.network.CategoryResponse;
import com.example.mealmate.network.DailyMealResponse;
import com.example.mealmate.network.MealService;
import com.example.mealmate.network.MealsRemoteDataSource;
import com.example.mealmate.network.NationalResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
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
        Log.d(TAG, "meal returned from database" + localDataSource.getDailyMeal());
        return remoteDataSource.getDailyMeal()
                .doOnNext(dailyMeal -> {
                            Log.i(TAG, "getDailyMeal: get meal from remote" + remoteDataSource.getDailyMeal().toString());

                            insertMeals(dailyMeal.getDailyMeals());


                        }
                ).map(
                        DailyMealResponse::getDailyMeals

                )
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public void deleteMeal(DailyMeal dailyMeal) {

    }

    @Override
    public void insertMeal(DailyMeal dailyMeal) {
        Log.i(TAG, "Inserting meal: " + dailyMeal.getIdMeal());
        localDataSource.insertMeal(new DailyMeal(
                "", "", "", "", "", "", "", "", ""));

    }

    @Override
    public void insertMeals(List<DailyMeal> dailyMeals) {

        localDataSource.insertMeals(dailyMeals);
//        Log.d(TAG, "insertMeals: Daily meals size: " + dailyMeals.size());
//        Completable.fromRunnable(() -> localDataSource.insertMeals(dailyMeals))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: Called");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: Called");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
//                    }
//                });


    }

    @Override
    public Observable<AreaResponse> getArea() {
        return remoteDataSource.getAreas()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CategoryResponse> getCategory() {
        return remoteDataSource.getCategories()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<NationalResponse> getNational() {
        return remoteDataSource.getNationalMeals()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<DailyMeal>> getMealDetails(String id) {
        return remoteDataSource.getMealDetails(id)
               .map(
                        DailyMealResponse::getDailyMeals

                )
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
