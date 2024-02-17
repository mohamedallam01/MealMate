package com.example.mealmate.weekplan;

public class DailyMealPlan {

    private String day;
    private String meal;

    public DailyMealPlan(String day, String meal) {
        this.day = day;
        this.meal = meal;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMealName() {
        return meal;
    }

    public void setMealName(String meal) {
        this.meal = meal;
    }
}
