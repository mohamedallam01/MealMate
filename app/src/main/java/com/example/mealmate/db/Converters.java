package com.example.mealmate.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static String fromList(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringList) {
            stringBuilder.append(string).append(",");
        }
        return stringBuilder.toString();
    }

    @TypeConverter
    public static List<String> toList(String string) {
        String[] stringArray = string.split(",");
        List<String> stringList = new ArrayList<>();
        for (String s : stringArray) {
            stringList.add(s);
        }
        return stringList;
    }

    @TypeConverter
    public static Object fromString(String value) {
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromObject(Object tags) {
        return new Gson().toJson(tags);
    }
}
