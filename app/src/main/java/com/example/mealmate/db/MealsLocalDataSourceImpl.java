package com.example.mealmate.db;

import android.content.Context;
import android.util.Log;

import com.example.mealmate.details.model.DetailedMeal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {


    private MealDao mealDao;

    private static final String TAG = "MealsLocalDataSourceImpl";

    private static MealsLocalDataSourceImpl localSource = null;

    private Flowable<List<DetailedMeal>> dailyMeals;

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
    public Flowable<List<DetailedMeal>> getDetailedMeals() {
        return mealDao.getDailyMeal();
    }

    @Override
    public Flowable<DetailedMeal> getSingleDetailedMeals(String id) {
        return mealDao.getMealById(id);
    }

    @Override
    public void insertMeal(DetailedMeal detailedMeal) {
        Completable.fromAction(() -> mealDao.insertMeal(detailedMeal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        e.printStackTrace();
                    }
                });

    }

    @Override
    public void insertMeals(List<DetailedMeal> detailedMeals) {
        Completable.fromAction(() -> mealDao.insertMeals(detailedMeals))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void changeFavoriteState(DetailedMeal detailedMeal) {
        Timber.d("changeFavoriteState");

        detailedMeal.setFavorite(!detailedMeal.getFavorite());
        Completable.fromAction(() -> mealDao.addToFavorite(detailedMeal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void deleteMeal(DetailedMeal detailedMeal) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                mealDao.deleteMeal(detailedMeal);
            }
        }).start();


    }

    @Override
    public Flowable<List<DetailedMeal>> getFavorites() {
        return mealDao.getFavorites();
    }
}



