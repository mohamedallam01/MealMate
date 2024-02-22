package com.example.mealmate.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealmate.MainActivity;
import com.example.mealmate.R;
import com.example.mealmate.db.AppDataBase;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.favorite.presenter.FavoritesPresenter;
import com.example.mealmate.favorite.presenter.FavoritesPresenterImpl;
import com.example.mealmate.favorite.view.FavoriteAdapter;
import com.example.mealmate.favorite.view.OnFavClickListener;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.DetailedMealResponse;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.example.mealmate.search.presenter.SearchPresenter;
import com.example.mealmate.search.presenter.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Search extends Fragment implements SearchView {

    SearchPresenterImpl searchPresenter;

    private List<DetailedMeal> detailedMealList;
    private SearchAdapter searchAdapter;

    private static final String TAG = "Favorite";

    MealsRepositoryImpl mealsRepository;

    RecyclerView rvSearch;
    MealDao mealDao;

    EditText etSearch;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) requireActivity()).setBottomNavigationVisibility(true);
        etSearch = view.findViewById(R.id.etSearch);




        rvSearch = view.findViewById(R.id.rvSearch);
        rvSearch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvSearch.setLayoutManager(linearLayoutManager);
        detailedMealList = new ArrayList<>();
        searchAdapter = new SearchAdapter(requireContext(), detailedMealList, searchPresenter);
        rvSearch.setAdapter(searchAdapter);

        searchPresenter = new SearchPresenterImpl(MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())),this);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                Log.d("SearchFragment", "Text changed: " + query);
                performSearch(query);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });


        mealDao = AppDataBase.getInstance(requireContext()).getMealDao();



    }

    private void performSearch(String query) {
        Log.d("SearchFragment", "Performing search with query: " + query);
        if (!query.isEmpty()) {
            searchPresenter.getSearchMeals(query);
        } else {
            searchAdapter.setList(new ArrayList<>());
        }
    }

    @Override
    public void showSearchResults(List<DetailedMeal> detailedMealList) {
        searchAdapter.setList(detailedMealList);
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}




// cvByCountry = view.findViewById(R.id.cvCountry);
//         cvByCategory = view.findViewById(R.id.cvCategory);
//         cvByIngredients = view.findViewById(R.id.cvIngredients);
//
//
//         cvByCountry.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        Navigation.findNavController(view).navigate(R.id.action_search_to_byCountry);
//        }
//        });
//        cvByCategory.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        Navigation.findNavController(view).navigate(R.id.action_search_to_byCategory);
//        }
//        });
//        cvByIngredients.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        Navigation.findNavController(view).navigate(R.id.action_search_to_byIngredients);
//        }
//        });