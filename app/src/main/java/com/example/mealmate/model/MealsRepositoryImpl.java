package com.example.mealmate.model;

import android.util.Log;

import com.example.mealmate.db.MealsLocalDataSource;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.details.view.DetailsView;
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

    DetailsView detailsView;

    public MealsRepositoryImpl(MealsLocalDataSource mealsLocalDataSource, MealsRemoteDataSource mealsRemoteDataSource) {

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
    public void deleteDailyMeal(DetailedMeal detailedMeal) {

    }

    @Override
    public void insertDailyMeal(DetailedMeal detailedMeal) {

    }

    @Override
    public Flowable<List<DetailedMeal>> getDailyMeal() {

        Flowable<List<DetailedMeal>> remoteMeals = remoteDataSource.getDailyMeal()

                .doOnNext(dailyMeal -> {
                            insertMeals(dailyMeal.getDailyMeals());
                        }
                ).map(
                        DailyMealResponse::getDailyMeals
                )
                .toFlowable(BackpressureStrategy.LATEST);


        Flowable<List<DetailedMeal>> localMeals = localDataSource.getDetailedMeals();


        return Flowable.concatArrayEager(remoteMeals, localMeals)
                .onErrorComplete(throwable -> {
                            throwable.printStackTrace();
                            return true;
                        }
                )
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteMeal(com.example.mealmate.details.model.DetailedMeal detailedMeal) {

        localDataSource.deleteMeal(detailedMeal);

    }

    @Override
    public void changeFavoriteState(com.example.mealmate.details.model.DetailedMeal detailedMeal) {
        localDataSource.changeFavoriteState(detailedMeal);
    }

    @Override
    public void insertMeals(List<DetailedMeal> detailedMeals) {

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
    public Flowable<DetailedMeal> getSingleDetailMeals(String id) {
        Flowable<DetailedMeal> remoteMeal = remoteDataSource.getMealDetails(id)
                .doOnNext(mealResponse -> {
                            Log.i(TAG, "getDetailedMeal: get meal from remote" + remoteDataSource.getMealDetails(id).toString());
                        }
                ).map(
                        detailedMealResponse ->
                                detailedMealResponse.detailedMeals.get(0)
                )
                .toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Log.e(TAG, "Error fetching detailed meal: " + error.getMessage());
                });

        Flowable<DetailedMeal> localMeals = localDataSource.getSingleDetailedMeals(id).onErrorComplete(
                throwable -> {
                    return true;
                }
        );

        return Flowable.concatArrayEager(remoteMeal, localMeals).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Flowable<List<DetailedMeal>> getFavorites() {
        return localDataSource.getFavorites().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
