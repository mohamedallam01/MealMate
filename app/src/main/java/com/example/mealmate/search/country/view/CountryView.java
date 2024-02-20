package com.example.mealmate.search.country.view;

import com.example.mealmate.search.country.model.Country;

import java.util.List;

public interface CountryView {

    void showCountriesData(List<Country> countryList);

    void showErrorMsg(String error);

}
