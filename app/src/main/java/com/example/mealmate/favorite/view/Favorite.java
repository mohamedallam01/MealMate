package com.example.mealmate.favorite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealmate.MainActivity;
import com.example.mealmate.R;
import com.example.mealmate.db.AppDataBase;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.favorite.presenter.FavoritesPresenterImpl;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Favorite extends Fragment implements OnFavClickListener {

    private List<DetailedMeal> detailedMealList;
    private FavoriteAdapter favoriteAdapter;

    private static final String TAG = "Favorite";

    MealsRepositoryImpl mealsRepository;

    RecyclerView rvFavorite;
    MealDao mealDao;

    FavoritesPresenterImpl favoritesPresenter;

    OnFavClickListener onFavClickListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) requireActivity()).setBottomNavigationVisibility(true);
        onFavClickListener = this;
        rvFavorite = view.findViewById(R.id.rvFavorite);
        rvFavorite.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvFavorite.setLayoutManager(linearLayoutManager);
        detailedMealList = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(requireContext(), detailedMealList, onFavClickListener,favoritesPresenter);
        rvFavorite.setAdapter(favoriteAdapter);

        favoritesPresenter = new FavoritesPresenterImpl(MealsRepositoryImpl.getInstance(
                 MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())));

        Flowable<List<DetailedMeal>> flowableFavoriteMeals = favoritesPresenter.getFavorites();

        flowableFavoriteMeals
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productList -> {
                    this.detailedMealList.clear();
                    this.detailedMealList.addAll(productList);
                    favoriteAdapter.notifyDataSetChanged();
                });


        mealDao = AppDataBase.getInstance(requireContext()).getMealDao();


    }

    @Override
    public void onMealClick(DetailedMeal detailedMeal) {
        favoritesPresenter.deleteMeal(detailedMeal);

    }
}