package com.example.mealmate.network;

import com.example.mealmate.search.category.model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("categories")
    public List<Category> categories;


    public List<Category> getCategories() {
        return categories;
    }
}
