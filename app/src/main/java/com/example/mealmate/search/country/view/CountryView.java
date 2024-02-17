package com.example.mealmate.search.country.view;

import com.example.mealmate.search.country.model.Area;

import java.util.List;

public interface CountryView {

    void showAreaData(List<Area> areaList);

    void showErrorMsg(String error);

}
