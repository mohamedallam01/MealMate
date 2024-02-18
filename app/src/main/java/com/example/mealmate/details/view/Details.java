package com.example.mealmate.details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.details.presenter.DetailsPresenterImpl;
import com.example.mealmate.home.model.DailyMeal;
import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.network.DailyMealResponse;

import java.util.List;


public class Details extends Fragment implements DetailsView {

    ImageView ivMealDetails;
    TextView tvMealName;

    DetailsPresenterImpl detailsPresenter;

    MealsRepository mealsRepository;

    DailyMealResponse dailyMealResponse;

    DailyMeal dailyMeal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivMealDetails = view.findViewById(R.id.ivMealDetails);
        tvMealName = view.findViewById(R.id.tvMealName);

        detailsPresenter = new DetailsPresenterImpl(this, mealsRepository);
        String mealID = DetailsArgs.fromBundle(getArguments()).getMealId();

        dailyMealResponse = new DailyMealResponse();

        detailsPresenter.getMealDetails(mealID,dailyMealResponse);


    }

    @Override
    public void showDetails(DailyMealResponse dailyMealResponse) {


        dailyMeal = dailyMealResponse.getDailyMeals().get(0);


        TextView tvMealName = getView().findViewById(R.id.tvMealName);
        //TextView mealCountryTextView = getView().findViewById(R.id.meal_country);
        //TextView txtInstructions = getView().findViewById(R.id.txtInstructions);
        ImageView ivMealDetails = getView().findViewById(R.id.ivMealDetails);

        tvMealName.setText(dailyMeal.getStrMeal());
        //mealCountryTextView.setText(pojoForMeal.getAreaOfMeal());



    }

    @Override
    public void showErrorMsg(String error) {

    }


    private String extractVideoIdFromUrl(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("https://www.youtube.com/")) {
            String[] urlParts = youtubeUrl.split("v=");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int ampersandPosition = videoId.indexOf('&');
                if (ampersandPosition != -1) {
                    videoId = videoId.substring(0, ampersandPosition);
                }
            }
        }
        return videoId;
    }
}