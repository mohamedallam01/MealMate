package com.example.mealmate.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.search.category.model.Category;
import com.example.mealmate.search.category.presenter.CategoryPresenterImpl;
import com.example.mealmate.search.country.model.Area;
import com.example.mealmate.search.country.presenter.CountryPresenterImpl;
import com.example.mealmate.search.country.view.AreaAdapter;

import java.util.ArrayList;
import java.util.List;


public class ByCategory extends Fragment implements CategoryView {

    private static final String TAG = "ByCategoryFragment";


    CategoryPresenterImpl categoryPresenter;

    RecyclerView rvCategory;

    private List<Category> categoryList;

    CategoryAdapter categoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCategory = view.findViewById(R.id.rvCategory);
        rvCategory.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(requireContext(), categoryList);

        categoryPresenter = new CategoryPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));

        rvCategory.setLayoutManager(gridLayoutManager);
        rvCategory.setAdapter(categoryAdapter);
        categoryPresenter.getAllCategories();

    }

    @Override
    public void showCategoryData(List<Category> categoryList) {

        categoryAdapter.setList(categoryList);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorMsg(String error) {

    }
}