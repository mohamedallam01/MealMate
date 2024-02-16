package com.example.mealmate.home.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.MainActivity;
import com.example.mealmate.R;
import com.example.mealmate.db.MealsLocalDataSource;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.home.model.Area;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.home.presenter.HomePresenterImpl;
import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSource;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Home extends Fragment implements HomeView {

    private static final String TAG = "HomeFragment";


    HomePresenterImpl homePresenter;

    ImageView ivDailyMeal;

    TextView tvDailyName;

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) requireActivity()).setBottomNavigationVisibility(true);

        ivDailyMeal = view.findViewById(R.id.ivDaily);
        tvDailyName = view.findViewById(R.id.tvDailyName);

        rvCountry = view.findViewById(R.id.rvCountry);
        rvCountry.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        areaList = new ArrayList<>();
        areaAdapter = new AreaAdapter(requireContext(), areaList);

        homePresenter = new HomePresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));

        rvCountry.setLayoutManager(linearLayoutManager);
        rvCountry.setAdapter(areaAdapter);
        homePresenter.getAllCountries();
        homePresenter.getMeal();



    }


    @Override
    public void showData(List<DailyMeal> dailyMealList) {


        if (!dailyMealList.isEmpty()) {
            DailyMeal dailyMeal = dailyMealList.get(0);
            tvDailyName.setText(dailyMeal.getStrMeal());
            Glide.with(requireContext())
                    .load(dailyMeal.getStrMealThumb())
                    .apply(new RequestOptions().override(200, 200))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivDailyMeal);
            Log.i(TAG, "showData: get in show data successful ");

        } else {
                tvDailyName.setText("No data available");
                ivDailyMeal.setImageResource(R.drawable.are_you_looking_for_quick_and_easy_appetizer_recipes_);
            Log.i(TAG, "showData: get in show data failed ");

        }

    }

    @Override
    public void showAreaData(List<Area> areaList) {
        areaAdapter.setList(areaList);
        areaAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {

    }
}