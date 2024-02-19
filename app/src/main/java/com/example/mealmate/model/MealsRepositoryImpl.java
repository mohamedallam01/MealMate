package com.example.mealmate.model;

import android.util.Log;

import com.example.mealmate.db.MealsLocalDataSource;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.details.presenter.DetailsPresenterImpl;
import com.example.mealmate.details.view.DetailsView;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.network.AreaResponse;
import com.example.mealmate.network.CategoryResponse;
import com.example.mealmate.network.DailyMealResponse;
import com.example.mealmate.network.DetailedMealResponse;
import com.example.mealmate.network.MealService;
import com.example.mealmate.network.MealsRemoteDataSource;
import com.example.mealmate.network.NationalResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepositoryImpl implements MealsRepository {

    private static final String TAG = "MealsRepositoryImpl";

    MealsLocalDataSource localDataSource;
    MealsRemoteDataSource remoteDataSource;

    MealService mealService;

    private static MealsRepositoryImpl mealsRepository = null;

    DetailsView detailsView;

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
    public void deleteDailyMeal(DailyMeal dailyMeal) {

    }

    @Override
    public void insertDailyMeal(DailyMeal dailyMeal) {

    }

    @Override
    public Flowable<List<DailyMeal>> getDailyMeal() {

        Flowable<List<DailyMeal>> remoteMeals = remoteDataSource.getDailyMeal().onErrorComplete(
                        throwable -> {
                            return true;
                        }
                )
                .doOnNext(dailyMeal -> {
                            insertMeals(dailyMeal.getDailyMeals());
                        }
                ).map(
                        DailyMealResponse::getDailyMeals
                )
                .toFlowable(BackpressureStrategy.LATEST);
        Flowable<List<DailyMeal>> localMeals = localDataSource.getDailyMeal().onErrorComplete(

                throwable -> {
                    throwable.printStackTrace();
                    return true;
                }
        );


        return Flowable.concatArrayEager(remoteMeals, localMeals)
                .onErrorComplete(throwable -> {
                            throwable.printStackTrace();
                            return true;
                        }
                )
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteMeal(DetailedMeal detailedMeal) {

        localDataSource.deleteMeal(detailedMeal);

    }

    @Override
    public void insertDetailedMeal(DetailedMeal detailedMeal) {
        Log.i(TAG, "Inserting meal: " + detailedMeal.getIdMeal());
        localDataSource.insertMeal(detailedMeal);
    }

    @Override
    public void insertMeals(List<DailyMeal> dailyMeals) {
        Completable.fromAction(() -> localDataSource.insertMeals(dailyMeals))
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
    public Flowable<List<DetailedMeal>> getDetailedMeal(String id) {
        return remoteDataSource.getMealDetails(id)
                .doOnNext(mealResponse -> {
                    Log.i(TAG, "getDetailedMeal: get meal from remote" + remoteDataSource.getMealDetails(id).toString());
                }
                ).map(
                        DetailedMealResponse::getDetailedMeals

                )
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Log.e(TAG, "Error fetching detailed meal: " + error.getMessage());
                });
    }

    @Override
    public Flowable<List<DetailedMeal>> getFavorites() {
        return localDataSource.getFavorites().subscribeOn(Schedulers.io());
    }


}
