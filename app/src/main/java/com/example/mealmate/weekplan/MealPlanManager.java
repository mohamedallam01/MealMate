package com.example.mealmate.weekplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPlanManager {
    private static final String TAG = "MealPlanManager";

    MealsRepository mealsRepository;

    private static final String PREF_KEY_MEAL = "meal_plan";

    public void saveMeal(Context context, String day, String mealId) {

        mealsRepository = new MealsRepositoryImpl(MealsLocalDataSourceImpl.getInstance(context), MealsRemoteDataSourceImpl.getInstance(context));
        mealsRepository.getSingleDetailMeals(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<DetailedMeal>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription subscription) {
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(@NonNull DetailedMeal detailedMeal) {
                        detailedMeal.setDay(day);
                        Log.d(TAG, "onNext: " + day);
                        saveDetailedMealToSharedPreferences(context, detailedMeal);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void saveDetailedMealToSharedPreferences(Context context, DetailedMeal detailedMeal) {
        List<DetailedMeal> mealPlan = getMealPlanFromSharedPreferences(context);
        mealPlan.add(detailedMeal);

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY_MEAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mealPlan);
        editor.putString(PREF_KEY_MEAL, json);
        editor.apply();
    }

    public List<DetailedMeal> getMealPlanFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY_MEAL, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PREF_KEY_MEAL, null);
        if (json != null) {

            return gson.fromJson(json, new TypeToken<List<DetailedMeal>>(){}.getType());
        } else {
            return new ArrayList<>();
        }
    }

    public void removeMealFromSharedPreferences(Context context, String mealId) {
        List<DetailedMeal> mealPlan = getMealPlanFromSharedPreferences(context);
        for (DetailedMeal meal : mealPlan) {
            if (meal.getIdMeal().equals(mealId)) {
                mealPlan.remove(meal);
                break;
            }
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY_MEAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mealPlan);
        editor.putString(PREF_KEY_MEAL, json);
        editor.apply();
    }


}


