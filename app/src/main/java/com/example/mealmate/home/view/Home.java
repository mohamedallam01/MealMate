package com.example.mealmate.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.home.presenter.HomePresenterImpl;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;

import java.util.List;


public class Home extends Fragment implements HomeView {

    private static final String TAG = "HomeFragment";


    HomePresenterImpl homePresenter;

    ImageView ivDailyMeal;

    TextView tvDailyName;

    CardView cvDailyMeal;




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
        cvDailyMeal = view.findViewById(R.id.cvDaily);



        homePresenter = new HomePresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));

        homePresenter.getMeal();





    }


    @Override
    public void showData(List<DailyMeal> dailyMealList) {

        DailyMeal dailyMeal = dailyMealList.get(0);
        if (!dailyMealList.isEmpty()) {
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


        cvDailyMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeDirections.ActionHomeToDetails action = HomeDirections.actionHomeToDetails(dailyMeal.getIdMeal());
                Navigation.findNavController(view).navigate(action);
            }
        });

    }



    @Override
    public void showErrorMsg(String error) {

    }
}