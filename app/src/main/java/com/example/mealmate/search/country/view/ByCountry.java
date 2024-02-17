package com.example.mealmate.search.country.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.search.country.model.Area;
import com.example.mealmate.search.country.presenter.CountryPresenterImpl;

import java.util.ArrayList;
import java.util.List;


public class ByCountry extends Fragment implements CountryView {

    private static final String TAG = "ByCountryFragment";


    CountryPresenterImpl countryPresenter;

    RecyclerView rvCountry;

    private List<Area> areaList;

    AreaAdapter areaAdapter;


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

        areaList = new ArrayList<>();
        areaAdapter = new AreaAdapter(requireContext(), areaList);

        countryPresenter = new CountryPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));

        rvCountry.setLayoutManager(linearLayoutManager);
        rvCountry.setAdapter(areaAdapter);
        countryPresenter.getAllCountries();

    }


    @Override
    public void showAreaData(List<Area> areaList) {


            areaAdapter.setList(areaList);
            areaAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorMsg (String error){

    }

}

