package com.example.mealmate.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mealmate.home.model.DailyMeal;

@Database(entities = {DailyMeal.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealDao getMealDao();
    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "mealsdb")
                    .build();
        }
        return instance;
    }
}
