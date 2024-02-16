package com.example.mealmate.home.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "daily_meal_table")
public class DailyMeal {

    @NonNull
    @PrimaryKey
    private String idMeal;
    private String strMeal;
    private String strDrinkAlternate;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private Object  strTags;
    private String strYoutube;
    private List<String> strIngredients;
    private List<String> strMeasures;


    public DailyMeal() {
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrDrinkAlternate() {
        return strDrinkAlternate;
    }

    public void setStrDrinkAlternate(String strDrinkAlternate) {
        this.strDrinkAlternate = strDrinkAlternate;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public Object getStrTags() {
        return strTags;
    }

    public void setStrTags(Object strTags) {
        this.strTags = strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public List<String> getStrIngredients() {
        return strIngredients;
    }

    public void setStrIngredients(List<String> strIngredients) {
        this.strIngredients = strIngredients;
    }

    public List<String> getStrMeasures() {
        return strMeasures;
    }

    public void setStrMeasures(List<String> strMeasures) {
        this.strMeasures = strMeasures;
    }


}

