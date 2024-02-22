package com.example.mealmate.search.country.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.search.country.model.Country;
import com.example.mealmate.search.country.presenter.CountryPresenterImpl;

import java.util.ArrayList;
import java.util.List;


public class ByCountry extends Fragment implements CountryView {

    private static final String TAG = "ByCountryFragment";


    CountryPresenterImpl countryPresenter;

    RecyclerView rvCountry;

    private List<Country> countryList;

    CountryAdapter countryAdapter;

    EditText etSearchCountry;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_by_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCountry = view.findViewById(R.id.rvCountry);
        rvCountry.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        countryList = new ArrayList<>();
        countryAdapter = new CountryAdapter(requireContext(), countryList);

        countryPresenter = new CountryPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));

        rvCountry.setLayoutManager(linearLayoutManager);
        rvCountry.setAdapter(countryAdapter);
        countryPresenter.getAllCountries();


        if (etSearchCountry != null) {
            etSearchCountry.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String query = charSequence.toString().trim();
                    Log.d("ByCountryFragment", "Text changed: " + query);
                    doSearch(query);

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }


    }

    private void doSearch(String query) {
        Log.d(TAG, "Performing search with query: " + query);
        if (!query.isEmpty()) {
            countryPresenter.getAllCountries();
        } else {
            countryAdapter.setList(new ArrayList<>());
        }
    }


    @Override
    public void showCountriesData(List<Country> countryList) {

        this.countryList.clear();
        countryAdapter.setList(countryList);
        countryAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorMsg(String error) {

    }

}

