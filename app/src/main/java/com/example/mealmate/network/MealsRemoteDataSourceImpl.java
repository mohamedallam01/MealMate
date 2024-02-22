package com.example.mealmate.network;

import android.content.Context;
import android.util.Log;

import com.example.mealmate.db.AppDataBase;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.search.country.model.Country;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {

    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealsRemoteDataSourceImpl";
    static MealService mealService;

    private MealDao mealDao;

    AppDataBase db;


    private static MealsRemoteDataSourceImpl mealsRemoteDataSource = null;

    private MealsRemoteDataSourceImpl(Context context) {

        int size = 10 * 24 * 24;
        Cache cache = new Cache(context.getCacheDir(),size);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        db = AppDataBase.getInstance(context);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        mealService = retrofit.create(MealService.class);


    }

    public static MealsRemoteDataSourceImpl getInstance(Context context) {

        if (mealsRemoteDataSource == null) {
            mealsRemoteDataSource = new MealsRemoteDataSourceImpl(context.getApplicationContext());
        }

        return mealsRemoteDataSource;

    }


    @Override
    public Observable<DailyMealResponse> getDailyMeal() {
        Log.i(TAG, "getDailyMeal: Fetching meal data from remote data source");

        return mealService.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(dailyMealResponse -> {
                    Log.i(TAG, "getDailyMeal: The Meal Is Here");
                })
                .onErrorResumeNext(error -> {
                    Log.e(TAG, "getDailyMeal: Error fetching meal data", error);
                    return Observable.error(error);
                });
    }

    @Override
    public Observable<CountriesResponse> getCountries() {
        return mealService.getArea()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(areaResponse -> {
                    areaResponse.getCountries();
                    Log.i(TAG, "getAreas: The Country Is Here");
                })
                .onErrorResumeNext(error -> {
                    Log.e(TAG, "getAreas: Error fetching Areas", error);
                    return Observable.error(error);
                });
    }

    @Override
    public Observable<CategoryResponse> getCategories() {
        return mealService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(categoryResponse -> {
                    categoryResponse.getCategories();
                    Log.i(TAG, "getCategories: The Category Is Here");
                })
                .onErrorResumeNext(error -> {
                    Log.e(TAG, "getCategories: Error fetching Categories", error);
                    return Observable.error(error);
                });
    }

    @Override
    public Observable<NationalResponse> getNationalMeals() {
        return mealService.getNationalMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(nationalResponse -> {
                    nationalResponse.getNationalMeals();
                    Log.i(TAG, "getNationalMeals: The National Meals Is Here");
                })
                .onErrorResumeNext(error -> {
                    Log.e(TAG, "getNationalMeals: Error fetching National Meals", error);
                    return Observable.error(error);
                });
    }

    @Override
    public Observable<DetailedMealResponse> getMealDetails(String id) {
        return mealService.getMealDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(detailedMealResponse -> {
                    if (detailedMealResponse == null) {
                        return Observable.empty();
                    } else {
                        return Observable.just(detailedMealResponse);
                    }
                })
                .doOnNext(detailedMealResponse -> {
                    List<DetailedMeal> detailedMeals = detailedMealResponse.getDetailedMeals();
                    Log.d(TAG, "getDetailedMeals:Success fetching Detailed Meals: " + detailedMeals.size());
                })
                .onErrorResumeNext(error -> {
                    Log.d(TAG, "getDetiledMeals: Error fetching Detailed Meals", error);
                    return Observable.error(error);
                });
    }

    @Override
    public Observable<DetailedMealResponse> getMealSearch(String key) {
        Log.i(TAG, "getMealSearch: Fetching meal data from remote data source");

        Single<DetailedMealResponse> mealsBySearch = mealService.getSearchByName(key);

        return mealsBySearch
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .doOnNext(detailedMealResponse -> {
                    detailedMealResponse.getDetailedMeals();
                })
                .map(detailedMealResponse -> detailedMealResponse);
    }


}


