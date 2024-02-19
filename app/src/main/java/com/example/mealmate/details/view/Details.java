package com.example.mealmate.details.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealmate.R;
import com.example.mealmate.db.AppDataBase;
import com.example.mealmate.db.MealDao;
import com.example.mealmate.db.MealsLocalDataSourceImpl;
import com.example.mealmate.details.model.DetailedMeal;
import com.example.mealmate.details.presenter.DetailsPresenterImpl;
import com.example.mealmate.favorite.view.OnFavClickListener;
import com.example.mealmate.model.MealsRepository;
import com.example.mealmate.model.MealsRepositoryImpl;
import com.example.mealmate.network.DetailedMealResponse;
import com.example.mealmate.network.MealsRemoteDataSourceImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;


public class Details extends Fragment implements DetailsView, OnMealClickListener,OnFavClickListener {


    private static final String TAG = "Details";
    ImageView ivMealDetails;
    TextView tvMealName;

    DetailsPresenterImpl detailsPresenter;

    String videoId;

    MealsRepository mealsRepository;

    DetailedMealResponse detailedMealResponse;

    DetailedMeal detailedMeal;

    TextView tvDetails;

    FloatingActionButton fabFavorite;

    OnMealClickListener onMealClickListener;

    private boolean isFavorite = false;

    OnFavClickListener onFavClickListener;

    MealDao mealDao;

    AppDataBase db;

    int count;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDataBase.getInstance(getContext());
        mealDao = db.getMealDao();




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
        tvDetails = view.findViewById(R.id.tvDetails);
        fabFavorite = view.findViewById(R.id.fabFavorite);

        onMealClickListener = this;
        onFavClickListener = this;

        detailsPresenter = new DetailsPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsLocalDataSourceImpl.getInstance(requireContext()), MealsRemoteDataSourceImpl.getInstance(requireContext())
        ));
        String mealID = DetailsArgs.fromBundle(getArguments()).getMealId();
        Log.d(TAG, "onViewCreated: " + mealID);
        detailsPresenter.getMealDetails(mealID);
        toggleFavoriteState();


    }

    @Override
    public void showDetails(List<DetailedMeal> detailedMealList) {


        if (detailedMealList != null && !detailedMealList.isEmpty()) {
            detailedMeal = detailedMealList.get(0);
            if (detailedMeal != null) {
                TextView tvMealName = getView().findViewById(R.id.tvMealName);
                ImageView ivMealDetails = getView().findViewById(R.id.ivMealDetails);
                tvMealName.setText(detailedMeal.getStrMeal());

                Glide.with(requireContext()).load(detailedMeal.getStrMealThumb())
                        .apply(new RequestOptions().override(200, 200))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(ivMealDetails);

            } else {
                Log.e(TAG, "showDetails: Detailed meal is null");
            }
        } else {
            Log.e(TAG, "showDetails: Detailed meal list is null or empty");
        }


        String videoUrl = detailedMeal.getStrYoutube();
        videoId = extractVideoIdFromUrl(videoUrl);

        if (videoId != null) {
            YouTubePlayerView youTubePlayerView = getView().findViewById(R.id.videoViewMeal);
            getLifecycle().addObserver(youTubePlayerView);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0);
                    Log.i(TAG, "onReady: " + videoId);
                }

                @Override
                public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                    super.onError(youTubePlayer, error);
                    Log.e(TAG, "YouTube Player Error: " + error.toString());
                }
            });
        } else {
            Toast.makeText(requireContext(), "The Video is not Available", Toast.LENGTH_SHORT).show();
        }


        String ingredientsText = detailedMeal.strIngredient1 != null ? detailedMeal.strIngredient1 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient2 != null ? detailedMeal.strIngredient2 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient3 != null ? detailedMeal.strIngredient3 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient4 != null ? detailedMeal.strIngredient4 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient5 != null ? detailedMeal.strIngredient5 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient6 != null ? detailedMeal.strIngredient6 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient7 != null ? detailedMeal.strIngredient7 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient8 != null ? detailedMeal.strIngredient8 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient9 != null ? detailedMeal.strIngredient9 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient10 != null ? detailedMeal.strIngredient10 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient11 != null ? detailedMeal.strIngredient11 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient12 != null ? detailedMeal.strIngredient12 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient13 != null ? detailedMeal.strIngredient13 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient14 != null ? detailedMeal.strIngredient14 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient15 != null ? detailedMeal.strIngredient15 + "\n" : "";
        ingredientsText += detailedMeal.strIngredient16 != null ? detailedMeal.strIngredient16 + "\n" : "";

        tvDetails.setText(ingredientsText);

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleFavoriteState();
            }


        });


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

    @Override
    public void onMealClick(DetailedMeal detailedMeal) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                detailsPresenter.addToFav(detailedMeal);
            }
        }).start();


    }


    private void toggleFavoriteState() {
        isFavorite = !isFavorite;

        if (isFavorite) {
            fabFavorite.setImageResource(R.drawable.baseline_favorite_24);
            Toast.makeText(requireContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mealDao.insertMeal(detailedMeal);

                }
            }).start();


        } else                                           {
            fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
            Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mealDao.deleteMeal(detailedMeal);


                }
            }).start();





        }
    }

    private boolean isMealInFavorites(String id) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                count = mealDao.countMealById(id);
            }
        }).start();
        return count > 0;
    }
}