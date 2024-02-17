package com.example.mealmate.weekplan;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MealPlanManager {

    private static final String PREF_KEY_MEAL = "meal_plan";

    public static void SaveMeal(Context context, DailyMealPlan dailyMealPlan){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY_MEAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dailyMealPlan);
        editor.putString(PREF_KEY_MEAL,json);
        editor.apply();

    }

    public static List<DailyMealPlan> getMealPlan(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY_MEAL, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PREF_KEY_MEAL, null);
        if (json != null) {
            return gson.fromJson(json, new TypeToken<List<DailyMealPlan>>(){}.getType());
        } else {
            return new ArrayList<>();
        }
    }
}
