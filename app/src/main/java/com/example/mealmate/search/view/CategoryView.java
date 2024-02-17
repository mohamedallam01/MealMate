package com.example.mealmate.search.view;

import com.example.mealmate.search.category.model.Category;

import java.util.List;

public interface CategoryView {

    void showCategoryData(List<Category> categoryList);

    void showErrorMsg(String error);

}
